package com.ecommerce.cartservice.exception;

public class CartItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CartItemNotFoundException(String msg) {
		super(msg);
	}
	
}
