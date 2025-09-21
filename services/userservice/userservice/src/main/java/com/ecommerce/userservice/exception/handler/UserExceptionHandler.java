package com.ecommerce.userservice.exception.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.userservice.exception.UserNotFoundException;
import com.ecommerce.userservice.exception.UserNotSaveException;
import com.ecommerce.userservice.exception.UserNotUpdateException;
import com.ecommerce.userservice.util.UserResponse;

import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

	@ExceptionHandler(UserNotSaveException.class)
    public ResponseEntity<UserResponse> handleUserNotSaveException(UserNotSaveException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
	
	@ExceptionHandler(UserNotUpdateException.class)
    public ResponseEntity<UserResponse> handleUserNotUpdateException(UserNotUpdateException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
  

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return buildResponse(false, HttpStatus.BAD_REQUEST, "Validation Failed", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserResponse> handleGeneralException(Exception ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }

    private ResponseEntity<UserResponse> buildResponse(boolean success, HttpStatus status, String message, Object data) {
    	UserResponse response = new UserResponse(success, status, message, data);
        return new ResponseEntity<>(response, status);
    }
}
