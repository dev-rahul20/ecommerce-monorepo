package com.ecommerce.orderservice.exception.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.orderservice.exception.OrderNotFoundException;
import com.ecommerce.orderservice.exception.OrderNotPlacedException;
import com.ecommerce.orderservice.util.OrderResponse;

import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class OrderExceptionHandler {

	@ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<OrderResponse> handleOrderNotFound(OrderNotFoundException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

	@ExceptionHandler(OrderNotPlacedException.class)
    public ResponseEntity<OrderResponse> handleOrderNotPlacedException(OrderNotPlacedException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
	    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<OrderResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return buildResponse(false, HttpStatus.BAD_REQUEST, "Validation Failed", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<OrderResponse> handleGeneralException(Exception ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }

    private ResponseEntity<OrderResponse> buildResponse(boolean success, HttpStatus status, String message, Object data) {
    	OrderResponse response = new OrderResponse(success, status, message, data);
        return new ResponseEntity<>(response, status);
    }
}
