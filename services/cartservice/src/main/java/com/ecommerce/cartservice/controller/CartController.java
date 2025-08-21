package com.ecommerce.cartservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.cartservice.entity.CartItem;
import com.ecommerce.cartservice.service.CartService;
import com.ecommerce.cartservice.util.CartResponse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

	private final CartService service;

	@GetMapping("/details/{userId}")
	public Mono<CartResponse> getDetailedCart(@PathVariable @NotNull @Positive Integer userId) {
		return service.getEnrichedCartByUser(userId).collectList().map(
				response -> new CartResponse(true, HttpStatus.OK, "Detailed Cart fetched Successfully!!", response));
	}

	@PostMapping("/add")
	public CartResponse addItem(@RequestParam @NotNull @Positive Integer userId,
			@RequestParam @NotNull @Positive Integer productId,
			@RequestParam(defaultValue = "1") @Positive Integer quantity) {
		CartItem item = service.addToCart(userId, productId, quantity);
		return new CartResponse(true, HttpStatus.OK, "Item added to Cart Successfully!!", item);
	}

	@GetMapping("/{userId}")
	public CartResponse getItems(@PathVariable @NotNull @Positive Integer userId) {
		List<CartItem> list = service.getCartByUser(userId);
		return new CartResponse(true, HttpStatus.OK, "Cart items fetched Successfully!!", list);
	}

	@DeleteMapping("/remove/{itemId}")
	public CartResponse removeItem(@PathVariable @NotBlank String itemId) {
		service.removeFromCart(itemId);
		return new CartResponse(true, HttpStatus.OK, "Cart item removed Successfully!!", true);
	}
	
	@PutMapping("/update-quantity")
	public CartResponse updateQuantity(@RequestParam String itemId,
	                                   @RequestParam @Positive Integer quantity) {
	    CartItem updatedItem = service.updateQuantity(itemId, quantity);
	    return new CartResponse(true, HttpStatus.OK, "Quantity updated", updatedItem);
	}
	
	@DeleteMapping("/clear/{userId}")
	public CartResponse clearCart(@PathVariable Integer userId) {
	    service.clearCartByUser(userId);
	    return new CartResponse(true, HttpStatus.OK, "Cart cleared", true);
	}



}
