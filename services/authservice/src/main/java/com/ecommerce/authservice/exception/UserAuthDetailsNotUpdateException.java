package com.ecommerce.authservice.exception;

public class UserAuthDetailsNotUpdateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAuthDetailsNotUpdateException(String msg) {
		super(msg);
	}
}
