package com.ecommerce.productservice.exception;

public class ProductNotDeleteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductNotDeleteException(String message) {
		super(message);
	}

}
