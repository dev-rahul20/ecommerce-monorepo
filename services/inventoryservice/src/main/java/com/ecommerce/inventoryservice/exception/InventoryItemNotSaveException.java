package com.ecommerce.inventoryservice.exception;

public class InventoryItemNotSaveException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public InventoryItemNotSaveException(String msg) {
		super(msg);
	}
	
}
