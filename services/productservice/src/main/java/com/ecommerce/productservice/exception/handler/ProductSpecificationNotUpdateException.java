package com.ecommerce.productservice.exception.handler;

public class ProductSpecificationNotUpdateException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public ProductSpecificationNotUpdateException(String msg) {
		super(msg);
	}
}
