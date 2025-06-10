package com.ecommerce.productservice.exception;

public class SupplierNotSaveException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SupplierNotSaveException(String message) {
		super(message);
	}
}
