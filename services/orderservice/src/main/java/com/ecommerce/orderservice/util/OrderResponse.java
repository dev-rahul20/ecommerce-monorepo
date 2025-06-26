package com.ecommerce.orderservice.util;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Data
public class OrderResponse {
	
	private boolean success;
	private HttpStatus status;
	private String message;
	private Object data;

}
