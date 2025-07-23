package com.ecommerce.inventoryservice.exception;

public class InventoryItemNotDeleteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InventoryItemNotDeleteException(String msg) {
		super(msg);
	}
	
}
