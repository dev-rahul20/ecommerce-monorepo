package com.ecommerce.authservice.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.authservice.dao.AuthDao;
import com.ecommerce.authservice.dto.AuthUserRequestDto;
import com.ecommerce.authservice.entity.AuthUser;
import com.ecommerce.authservice.exception.UserAuthDetailsNotSaveException;
import com.ecommerce.authservice.exception.UserAuthDetailsNotUpdateException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final ModelMapper mapper;
	private final AuthDao dao;
	
	@Transactional
	@Override
	public Long createAuthUser(AuthUserRequestDto dto) {
		
		AuthUser authUser = mapper.map(dto, AuthUser.class);
		
		return Optional.ofNullable(dao.createAuthUser(authUser))
					   .filter(id -> id > 0)
					   .orElseThrow(() -> new UserAuthDetailsNotSaveException("User Auth Details Not Save!!"));
	}

	@Transactional
	@Override
	public Long updateAuthUser(@Valid AuthUserRequestDto dto) {
		
		AuthUser authUser = mapper.map(dto, AuthUser.class);
		
		return Optional.ofNullable(dao.updateAuthUser(authUser))
					   .filter(id -> id > 0)
					   .orElseThrow(() -> new UserAuthDetailsNotUpdateException("User Auth Details Not Update!!"));
	}
}
