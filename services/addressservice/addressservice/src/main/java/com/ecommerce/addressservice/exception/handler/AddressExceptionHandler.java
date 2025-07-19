package com.ecommerce.addressservice.exception.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.addressservice.exception.AddressNotFoundException;
import com.ecommerce.addressservice.exception.AddressNotSaveException;
import com.ecommerce.addressservice.exception.AddressNotUpdateException;
import com.ecommerce.addressservice.exception.CityNotFoundException;
import com.ecommerce.addressservice.exception.CountryNotFoundException;
import com.ecommerce.addressservice.exception.StateNotFoundException;
import com.ecommerce.addressservice.exception.UserNotFoundException;
import com.ecommerce.addressservice.util.AddressResponse;

import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AddressExceptionHandler {

	@ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<AddressResponse> handleAddressNotFound(AddressNotFoundException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

	@ExceptionHandler(AddressNotSaveException.class)
    public ResponseEntity<AddressResponse> handleAddressNotSaveException(AddressNotSaveException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
	
	@ExceptionHandler(AddressNotUpdateException.class)
    public ResponseEntity<AddressResponse> handleAddressNotUpdateException(AddressNotUpdateException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
	
    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<AddressResponse> handleCountryNotFound(CountryNotFoundException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
    
    @ExceptionHandler(StateNotFoundException.class)
    public ResponseEntity<AddressResponse> handleStateNotFound(StateNotFoundException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
    
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<AddressResponse> handleCityNotFound(CityNotFoundException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<AddressResponse> handleUserNotFound(UserNotFoundException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AddressResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return buildResponse(false, HttpStatus.BAD_REQUEST, "Validation Failed", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AddressResponse> handleGeneralException(Exception ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }

    private ResponseEntity<AddressResponse> buildResponse(boolean success, HttpStatus status, String message, Object data) {
    	AddressResponse response = new AddressResponse(success, status, message, data);
        return new ResponseEntity<>(response, status);
    }
}
