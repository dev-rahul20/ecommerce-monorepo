package com.ecommerce.productservice.exception;

public class ProductImageNotSaveException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductImageNotSaveException(String message) {
		super(message);
	}
	
}
