package com.ecommerce.addressservice.exception;

public class CountryNotFoundException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;
	
	public CountryNotFoundException(String message) {
        super(message);
    }
}
