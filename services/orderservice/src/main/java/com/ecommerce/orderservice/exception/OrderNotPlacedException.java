package com.ecommerce.orderservice.exception;

public class OrderNotPlacedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OrderNotPlacedException(String message) {
		super(message);
	}

}
