package com.ecommerce.wishlistservice.service;

import java.util.List;

import com.ecommerce.wishlistservice.entity.WishlistItem;

public interface WishlistService {

	WishlistItem addToWishlist(Integer userId, Integer productId);
	List<WishlistItem> getWishlistByUser(Integer userId);
	void removeFromWishlist(String itemId);

}
