package com.ecommerce.productservice.exception;

public class ProductImageNotDeleteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductImageNotDeleteException(String msg) {
		super(msg);
	}
}
