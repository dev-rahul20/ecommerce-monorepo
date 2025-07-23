package com.ecommerce.wishlistservice.service;

import java.util.List;

import com.ecommerce.wishlistservice.dto.WishlistItemDTO;
import com.ecommerce.wishlistservice.entity.WishlistItem;

import reactor.core.publisher.Flux;

public interface WishlistService {

	WishlistItem addToWishlist(Integer userId, Integer productId);
	List<WishlistItem> getWishlistByUser(Integer userId);
	void removeFromWishlist(String itemId);
	Flux<WishlistItemDTO> getEnrichedWishlistByUser(Integer userId);

}
