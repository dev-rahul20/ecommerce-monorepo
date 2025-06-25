package com.ecommerce.productservice.exception;

public class ProductImageNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductImageNotFoundException(String msg) {
		super(msg);
	}
	
}
