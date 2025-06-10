package com.ecommerce.productservice.exception;

public class ParentCategoryNotSaveException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParentCategoryNotSaveException(String message) {
		super(message);
	}
	
}
