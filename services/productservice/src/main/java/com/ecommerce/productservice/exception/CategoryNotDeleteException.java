package com.ecommerce.productservice.exception;

public class CategoryNotDeleteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CategoryNotDeleteException(String message) {
		super(message);
	}
	
}
