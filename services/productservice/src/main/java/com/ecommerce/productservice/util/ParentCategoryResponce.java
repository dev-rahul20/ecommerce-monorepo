package com.ecommerce.productservice.util;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParentCategoryResponce {

	private boolean success;
	private HttpStatus status;
	private String message;
	private Object data;
	
}
