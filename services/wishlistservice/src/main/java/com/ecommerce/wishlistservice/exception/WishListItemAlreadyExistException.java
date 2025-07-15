package com.ecommerce.wishlistservice.exception;

public class WishListItemAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WishListItemAlreadyExistException(String msg) {
		super(msg);
	}
}
