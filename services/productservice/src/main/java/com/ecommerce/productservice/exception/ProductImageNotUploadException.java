package com.ecommerce.productservice.exception;

public class ProductImageNotUploadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductImageNotUploadException(String message) {
		super(message);
	}
	
}
