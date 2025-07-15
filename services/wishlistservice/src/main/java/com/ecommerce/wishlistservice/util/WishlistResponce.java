package com.ecommerce.wishlistservice.util;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WishlistResponce {

	private boolean success;
	private HttpStatus status;
	private String message;
	private Object data;
	
}
