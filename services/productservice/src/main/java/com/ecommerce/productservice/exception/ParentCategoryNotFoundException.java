package com.ecommerce.productservice.exception;

public class ParentCategoryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParentCategoryNotFoundException(String message) {
		super(message);
	}
	
}
