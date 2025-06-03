package com.ecommerce.addressservice.util;

import org.springframework.http.HttpStatus;

public class AddressResponse {

	private boolean success;
	private HttpStatus status;
	private String message;
	private Object data;
	
	public AddressResponse(boolean success, HttpStatus status, String message, Object data) {
		this.success = success;
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public AddressResponse(boolean success, String message, Object data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
	
	
}
