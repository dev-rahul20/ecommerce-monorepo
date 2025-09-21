package com.ecommerce.userservice.user.service;


import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecommerce.userservice.constants.AuthSyncStatus;
import com.ecommerce.userservice.dto.AuthCreateResponse;
import com.ecommerce.userservice.dto.AuthUserRequestDto;
import com.ecommerce.userservice.dto.CreateUserCompositeDto;
import com.ecommerce.userservice.dto.UserResponseDto;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.exception.UserNotFoundException;
import com.ecommerce.userservice.exception.UserNotSaveException;
import com.ecommerce.userservice.user.dao.UserDao;

import lombok.RequiredArgsConstructor;
import reactor.util.retry.Retry;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserDao dao;
	private final WebClient authWebClient;
	private final ModelMapper modelMapper;
	private final ExecutorService syncExecutor = Executors.newFixedThreadPool(4);
	private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	
	@Value("${auth.service.endpoints.createauthuser}")
	private String createAuthUserDetailsUrl;
	
	@Value("${auth.service.endpoints.updateauthuser}")
	private String updateAuthUserDetailsUrl;
	
	
	

	@Override
	@Transactional
	public Long createUser(CreateUserCompositeDto dto) {

		User user = modelMapper.map(dto.getUserRequest(), User.class);
		
		user.setAuthSyncStatus(AuthSyncStatus.NOT_STARTED);
		
		Long createdUserId = Optional.ofNullable(dao.createUser(user))
									 .filter(id -> id > 0)
									 .orElseThrow(() -> new UserNotSaveException("Error while saving address"));

		AuthUserRequestDto userAuthDetails = modelMapper.map(dto.getAuthUserRequest(), AuthUserRequestDto.class);
		
		/**Set userId into AuthUser Details*/
		userAuthDetails.setUserServiceId(createdUserId);
		
		/** Here we register synchronization. This just tells Spring:  
		 * “When (and only if) this transaction commits successfully, run this method afterwards.”  
		 * */
	    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
		
	    	// After commit succeeds, Spring executes your afterCommit() hook
	        @Override
	        public void afterCommit() {
	        	
	        	syncExecutor.submit(() -> syncWithAuthService(createdUserId, userAuthDetails));
	        }
	    });
	    
		return createdUserId;
	}

	/**
     * Called after commit, runs outside DB transaction.
     * Tries a few short retries, if fails marks user PENDING and updates retry metadata.
     * This is an eventual consistency (saga-style) orchestration using synchronous HTTP calls with retry and fallback to PENDING state.
     */
    private void syncWithAuthService(Long userId, AuthUserRequestDto authReq) {
        
    	try {
            AuthCreateResponse response = authWebClient.post()
                    								   .uri(createAuthUserDetailsUrl)
                    								   .bodyValue(authReq)
                    								   .retrieve()
                    								   .bodyToMono(AuthCreateResponse.class)
                    								   .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)).jitter(0.2))
                    								   .timeout(Duration.ofSeconds(5))
                    								   .block();

            // Validate and parse AUTH ID from Response
            Long authUserId = Optional.ofNullable(response)
                    				  .map(AuthCreateResponse::data)    
                    				  .map(Object::toString)
                    				  .map(s -> {
                    					  try { 
                    						  return Long.valueOf(s);
                    					  }
                    					  catch (NumberFormatException ex) {
                    						  throw new IllegalStateException("Auth service returned non-numeric id: " + s, ex);
                    					  }
                    				  })
                    				  .filter(id -> id > 0)
                    				  .orElseThrow(() -> new IllegalStateException("Invalid auth-service response for userId=" + userId + " response=" + response));

            // Success -> Mark User to SYNCED
            Optional.ofNullable(dao.getByUserId(userId))
            		.ifPresent(user -> {
            				   user.setAuthSyncStatus(AuthSyncStatus.SYNCED);
            				   user.setAuthLastAttemptAt(Instant.now());
            				   user.setAuthRetryCount(0);
            				   
            				   dao.updateUser(user);
            });

            log.info("Auth sync success for userId={} authId={}", userId, authUserId);

        } catch (Exception ex) {
            
            log.error("Auth sync failed for userId={}: {}", userId, ex.toString(), ex);

            // Failed -> Mark User as Pending
            Optional.ofNullable(dao.getByUserId(userId)).ifPresent(user -> {
                									Integer prev = user.getAuthRetryCount();
                												   user.setAuthRetryCount((prev == null ? 0 : prev) + 1);
                												   user.setAuthLastAttemptAt(Instant.now());
                												   user.setAuthSyncStatus(AuthSyncStatus.PENDING);
                				
                												   try {
                											            dao.updateUser(user);
                											            log.info("Marked user {} PENDING (retry={})", userId, user.getAuthRetryCount());
                											        } catch (Exception e) {
                											            log.error("Failed to update user {} to PENDING: {}", userId, e.toString(), e);
                											        }
            });
        }
    }

	@Override
	@Transactional
	public List<UserResponseDto> getAllUsers() {
		return dao.getAllUsers();
	}

	@Override
	@Transactional(readOnly = true)
	public UserResponseDto getByUserId(Long userId) {
		
  		User user = dao.getByUserId(userId);
		
  		if(user == null) throw new UserNotFoundException("User not found with id : "+userId);
  		
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	@Transactional
	public Long updateUser(Long userId, UserResponseDto dto) {

		User user = modelMapper.map(dto, User.class);
		user.setUserId(userId);
		Long updatedUserId = dao.updateUser(user);

		return Optional.ofNullable(updatedUserId)
					   .filter(id -> id > 0)
					   .orElseThrow(() -> new UserNotFoundException("Error while Updating User"));
	}

	@Override
	@Transactional
	public Boolean deleteUser(Long userId) {
		
		if(dao.getByUserId(userId) == null) throw new UserNotFoundException("User not found for deletion");
		
		return dao.deleteUser(userId);
	}

}
