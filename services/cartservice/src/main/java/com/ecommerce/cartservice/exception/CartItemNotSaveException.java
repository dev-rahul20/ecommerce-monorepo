package com.ecommerce.cartservice.exception;

public class CartItemNotSaveException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CartItemNotSaveException(String msg) {
		super(msg);
	}
	
}
