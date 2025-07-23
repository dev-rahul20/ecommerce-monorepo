package com.ecommerce.wishlistservice.dao;

import com.ecommerce.wishlistservice.entity.WishlistItem;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface WishlistRepository extends CrudRepository<WishlistItem, String> {
    
	List<WishlistItem> findByUserId(Integer userId);
    boolean existsByUserIdAndProductId(Integer userId, Integer productId);
    void deleteById(String itemId);
}
