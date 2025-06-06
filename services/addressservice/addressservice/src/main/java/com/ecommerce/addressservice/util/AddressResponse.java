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
public class AddressResponse {

	//Few properties we mark them as final to work with @@RequiredArgsConstructor or we can also use @NonNull
	private final boolean success;
	private final HttpStatus status;
	private final String message;
	private Object data;
	
}
