package com.ecommerce.productservice.exception;

public class ProductSpecificationNotSaveException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ProductSpecificationNotSaveException(String message) {
		super(message);
	}
	
}
