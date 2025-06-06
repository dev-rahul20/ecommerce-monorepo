package com.ecommerce.userservice.exception;

public class UserNotUpdateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotUpdateException(String message) {
		super(message);
	}
	
}
