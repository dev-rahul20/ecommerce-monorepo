package com.ecommerce.cartservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;

import com.ecommerce.cartservice.client.ProductClient;
import com.ecommerce.cartservice.dao.CartRepository;
import com.ecommerce.cartservice.dto.CartItemDTO;
import com.ecommerce.cartservice.entity.CartItem;
import com.ecommerce.cartservice.exception.CartItemAlreadyExistException;
import com.ecommerce.cartservice.exception.CartItemNotFoundException;
import com.ecommerce.cartservice.exception.CartItemNotSaveException;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

	private final CartRepository repository;
	private final ProductClient productClient;

	@Override
	public Flux<CartItemDTO> getEnrichedCartByUser(Integer userId) {

		List<CartItem> entries = repository.findByUserId(userId);

		return Flux.fromIterable(entries)
				.flatMap(entry -> productClient.getProductById(entry.getProductId()).map(product -> {

					CartItemDTO dto = new CartItemDTO();
					dto.setCartItemId(entry.getCartItemId());
					dto.setUserId(userId);
					dto.setProduct(product);
					dto.setQuantity(entry.getQuantity());
					dto.setAddedAt(entry.getAddedAt());

					return dto;
				}));

	}

	@Override
	public CartItem addToCart(Integer userId, Integer productId, Integer quantity) {

		if (repository.existsByUserIdAndProductId(userId, productId)) {
			throw new CartItemAlreadyExistException("Cart Item Already Exist!!");
		}

		productClient.getProductById(productId).blockOptional()
				.orElseThrow(() -> new RuntimeException("Product not found"));

		CartItem item = CartItem.builder().cartItemId(UUID.randomUUID().toString()).userId(userId).productId(productId)
				.quantity(quantity).addedAt(LocalDateTime.now()).build();

		try {
			return repository.save(item);
		} catch (RedisConnectionFailureException e) {
			throw new CartItemNotSaveException("Redis server unavailable. Could not save item.");
		}

	}

	@Override
	public List<CartItem> getCartByUser(Integer userId) {
		return repository.findByUserId(userId);
	}

	@Override
	public void removeFromCart(String itemId) {
		repository.deleteById(itemId);
	}

	@Override
	public CartItem updateQuantity(String itemId, Integer quantity) {
		CartItem item = repository.findById(itemId)
				.orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));
		item.setQuantity(quantity);
		try {
			return repository.save(item);
		} catch (RedisConnectionFailureException e) {
			throw new CartItemNotSaveException("Redis server unavailable. Could not update quantity.");
		}
	}

	@Override
	public void clearCartByUser(Integer userId) {
		repository.deleteByUserId(userId);
	}

}
