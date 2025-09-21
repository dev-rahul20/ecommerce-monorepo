package com.ecommerce.authservice.exception.handler;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.authservice.dto.AuthResponse;
import com.ecommerce.authservice.exception.UserAuthDetailsNotSaveException;
import com.ecommerce.authservice.exception.UserAuthDetailsNotUpdateException;

@ControllerAdvice
public class UserAuthDetailsExceptionHandler {

	@ExceptionHandler(UserAuthDetailsNotSaveException.class)
    public ResponseEntity<AuthResponse> handleUserAuthDetailsNotSaveException(UserAuthDetailsNotSaveException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
	
	@ExceptionHandler(UserAuthDetailsNotUpdateException.class)
    public ResponseEntity<AuthResponse> handleUserAuthDetailsNotUpdateException(UserAuthDetailsNotUpdateException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AuthResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return buildResponse(false, HttpStatus.BAD_REQUEST, "Validation Failed", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponse> handleGeneralException(Exception ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }

    private ResponseEntity<AuthResponse> buildResponse(boolean success, HttpStatus status, String message, Object data) {
    	AuthResponse response = new AuthResponse(success, status, message, data);
        return new ResponseEntity<>(response, status);
    }
}
