package com.ecommerce.authservice.exception;

/** Same message for unknown user, wrong password, locked and disabled accounts, so callers cannot probe which. */
public class InvalidCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidCredentialsException() {
		super("Invalid username or password");
	}

	public InvalidCredentialsException(String message) {
		super(message);
	}
}
