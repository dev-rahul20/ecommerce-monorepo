package com.ecommerce.cartservice.exception;

public class CartItemAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CartItemAlreadyExistException(String msg) {
		super(msg);
	}

	

}
