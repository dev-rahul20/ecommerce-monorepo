package com.ecommerce.wishlistservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;

import com.ecommerce.wishlistservice.client.ProductClient;
import com.ecommerce.wishlistservice.dao.WishlistRepository;
import com.ecommerce.wishlistservice.dto.WishlistItemDTO;
import com.ecommerce.wishlistservice.entity.WishlistItem;
import com.ecommerce.wishlistservice.exception.WishListItemAlreadyExistException;
import com.ecommerce.wishlistservice.exception.WishListItemNotSaveException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

	private final ProductClient productClient;
    private final WishlistRepository repository;

    
    @Override
    public Flux<WishlistItemDTO> getEnrichedWishlistByUser(Integer userId) {
        
    	List<WishlistItem> entries = repository.findByUserId(userId);

        return Flux.fromIterable(entries)
                   .flatMap(entry -> productClient.getProductById(entry.getProductId())
                   .map(product -> {
                        
                	    WishlistItemDTO dto = new WishlistItemDTO();
                        dto.setWishlistId(entry.getWishlistId());
                        dto.setUserId(userId);
                        dto.setProduct(product);
                        dto.setAddedAt(entry.getAddedAt());
                        
                        return dto;
                   }));
    }

    
    @Override
    public WishlistItem addToWishlist(Integer userId, Integer productId) {
   
    	if (!repository.existsByUserIdAndProductId(userId, productId)) {
            
    		WishlistItem item = WishlistItem.builder()
            								.wishlistId(UUID.randomUUID().toString())
                    						.userId(userId)
                    						.productId(productId)
                    						.addedAt(LocalDateTime.now())
                    						.build();
            
    		try {
    		    return repository.save(item);
    		} catch (RedisConnectionFailureException e) {
    		    throw new WishListItemNotSaveException("Redis server unavailable. Could not save item.");
    		}

        }
    	
        throw new WishListItemAlreadyExistException("Wishlist Item Already Exist Exception!");
    }

    @Override
    public List<WishlistItem> getWishlistByUser(Integer userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public void removeFromWishlist(String itemId) {
        
    	repository.deleteById(itemId);
    }
    
}
