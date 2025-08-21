package com.ecommerce.cartservice.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.cartservice.exception.CartItemAlreadyExistException;
import com.ecommerce.cartservice.exception.CartItemNotFoundException;
import com.ecommerce.cartservice.exception.CartItemNotSaveException;
import com.ecommerce.cartservice.util.CartResponse;

@ControllerAdvice
public class CartExceptionHandler {

	@ExceptionHandler(CartItemNotSaveException.class)
	public ResponseEntity<CartResponse> handleCartItemNotSaveException(CartItemNotSaveException ex) {
		return buildResponse(false, HttpStatus.BAD_REQUEST, ex.getMessage(), null);
	}

	@ExceptionHandler(CartItemAlreadyExistException.class)
	public ResponseEntity<CartResponse> handleCartItemAlreadyExistsException(CartItemAlreadyExistException ex) {
		return buildResponse(false, HttpStatus.CONFLICT, ex.getMessage(), null);
	}
	
	@ExceptionHandler(CartItemNotFoundException.class)
	public ResponseEntity<CartResponse> handleCartItemNotFoundException(CartItemNotFoundException ex) {
		return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CartResponse> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
		return buildResponse(false, HttpStatus.BAD_REQUEST, "Validation Failed", errors);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<CartResponse> handleGeneralException(Exception ex) {
		return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
	}

	private ResponseEntity<CartResponse> buildResponse(boolean success, HttpStatus status, String message,
			Object data) {
		CartResponse response = new CartResponse(success, status, message, data);
		return new ResponseEntity<>(response, status);
	}

}
