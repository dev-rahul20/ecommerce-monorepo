package com.ecommerce.productservice.exception;

public class ProductNotUpdateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductNotUpdateException(String message) {
		super(message);
	}

}
