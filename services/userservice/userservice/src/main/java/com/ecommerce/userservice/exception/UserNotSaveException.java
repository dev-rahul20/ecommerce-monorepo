package com.ecommerce.userservice.exception;

public class UserNotSaveException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotSaveException(String message) {
		super(message);
	}
	
}
