package com.ecommerce.wishlistservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecommerce.wishlistservice.dao.WishlistRepository;
import com.ecommerce.wishlistservice.entity.WishlistItem;
import com.ecommerce.wishlistservice.exception.WishListItemAlreadyExistException;
import com.ecommerce.wishlistservice.exception.WishListItemNotSaveException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {


    private final WishlistRepository repository;

    @Override
    public WishlistItem addToWishlist(Integer userId, Integer productId) {
   
    	if (!repository.existsByUserIdAndProductId(userId, productId)) {
            
    		WishlistItem item = WishlistItem.builder()
            								.wishlistId(UUID.randomUUID().toString())
                    						.userId(userId)
                    						.productId(productId)
                    						.addedAt(LocalDateTime.now())
                    						.build();
            
            return Optional.ofNullable(repository.save(item)).orElseThrow(() -> new WishListItemNotSaveException("Wishlist Item Not Saved!"));
        }
    	
        throw new WishListItemAlreadyExistException("Wishlist Item Already Exist Exception!");
    }

    @Override
    public List<WishlistItem> getWishlistByUser(Integer userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public void removeFromWishlist(String itemId) {
        
    	repository.deleteByUserIdAndProductId(itemId);
    }
    
}
