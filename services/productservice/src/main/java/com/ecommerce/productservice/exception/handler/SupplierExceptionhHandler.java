package com.ecommerce.productservice.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.productservice.exception.SupplierNotDeleteException;
import com.ecommerce.productservice.exception.SupplierNotFoundException;
import com.ecommerce.productservice.exception.SupplierNotSaveException;
import com.ecommerce.productservice.exception.SupplierNotUpdateException;
import com.ecommerce.productservice.util.SupplierResponce;

@ControllerAdvice
public class SupplierExceptionhHandler {

	@ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<SupplierResponce> handleSupplieryNotFoundException(SupplierNotFoundException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
	
	@ExceptionHandler(SupplierNotSaveException.class)
    public ResponseEntity<SupplierResponce> handleSupplierNotSaveException(SupplierNotSaveException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
	@ExceptionHandler(SupplierNotUpdateException.class)
    public ResponseEntity<SupplierResponce> handleSupplierNotUpdateException(SupplierNotUpdateException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
	@ExceptionHandler(SupplierNotDeleteException.class)
    public ResponseEntity<SupplierResponce> handleSupplierNotDeleteException(SupplierNotDeleteException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<SupplierResponce> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return buildResponse(false, HttpStatus.BAD_REQUEST, "Validation Failed", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SupplierResponce> handleGeneralException(Exception ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
    
    private ResponseEntity<SupplierResponce> buildResponse(boolean success, HttpStatus status, String message, Object data) {
    	SupplierResponce response = new SupplierResponce(success, status, message, data);
        return new ResponseEntity<>(response, status);
    }
	
}
