package com.ecommerce.cartservice.util;


import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartResponse {
	
	private boolean success;
	private HttpStatus status;
	private String message;
	private Object data;

}
