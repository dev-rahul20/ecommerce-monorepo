package com.ecommerce.cartservice.service;

import java.util.List;

import com.ecommerce.cartservice.dto.CartItemDTO;
import com.ecommerce.cartservice.entity.CartItem;

import reactor.core.publisher.Flux;

public interface CartService {

	Flux<CartItemDTO> getEnrichedCartByUser(Integer userId);

	CartItem addToCart(Integer userId, Integer productId, Integer quantity);

	List<CartItem> getCartByUser(Integer userId);

	void removeFromCart(String itemId);

	CartItem updateQuantity(String itemId, Integer quantity);

	void clearCartByUser(Integer userId);

}
