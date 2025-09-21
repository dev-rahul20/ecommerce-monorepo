package com.ecommerce.authservice.exception;

public class UserAuthDetailsNotSaveException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAuthDetailsNotSaveException(String message) {
		super(message);
	}
	
}
