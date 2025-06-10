package com.ecommerce.productservice.exception;

public class ParentCategoryNotDeleteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParentCategoryNotDeleteException(String message) {
		super(message);
	}
	
}
