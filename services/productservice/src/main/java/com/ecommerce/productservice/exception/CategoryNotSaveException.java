package com.ecommerce.productservice.exception;

public class CategoryNotSaveException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CategoryNotSaveException(String message) {
		super(message);
	}
}
