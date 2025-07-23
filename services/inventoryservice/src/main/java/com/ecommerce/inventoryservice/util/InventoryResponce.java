package com.ecommerce.inventoryservice.util;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InventoryResponce {

	private boolean success;
	private HttpStatus status;
	private String message;
	private Object data;
	
}
