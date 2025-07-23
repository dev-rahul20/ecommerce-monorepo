package com.ecommerce.inventoryservice.exception;

public class InventoryItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InventoryItemNotFoundException(String msg) {
		super(msg);
	}
	
}
