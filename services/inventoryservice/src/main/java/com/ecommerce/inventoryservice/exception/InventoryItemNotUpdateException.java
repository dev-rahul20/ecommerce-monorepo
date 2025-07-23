package com.ecommerce.inventoryservice.exception;

public class InventoryItemNotUpdateException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InventoryItemNotUpdateException(String msg) {
		super(msg);
	}
}
