package com.ecommerce.addressservice.util;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserResponse {

	private boolean success;
	private HttpStatus status;
	private String message;
	private Object data;
	
}