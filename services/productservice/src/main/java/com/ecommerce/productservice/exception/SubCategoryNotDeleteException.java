package com.ecommerce.productservice.exception;

public class SubCategoryNotDeleteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SubCategoryNotDeleteException(String message) {
		super(message);
	}

}
