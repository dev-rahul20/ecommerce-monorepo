package com.ecommerce.productservice.util;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CategoryResponce {

	private boolean success;
	private HttpStatus status;
	private String message;
	private Object data;
	
}
