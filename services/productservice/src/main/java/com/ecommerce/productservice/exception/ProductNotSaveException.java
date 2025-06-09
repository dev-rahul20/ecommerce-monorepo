package com.ecommerce.productservice.exception;

public class ProductNotSaveException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductNotSaveException(String message) {
		super(message);
	}

}
