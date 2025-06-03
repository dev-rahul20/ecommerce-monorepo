package com.ecommerce.addressservice.exception;

public class AddressNotSaveException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AddressNotSaveException(String message) {
		super(message);
	}
	
}
