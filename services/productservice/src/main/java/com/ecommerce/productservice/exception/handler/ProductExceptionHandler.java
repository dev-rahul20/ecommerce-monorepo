package com.ecommerce.productservice.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.exception.ProductNotSaveException;
import com.ecommerce.productservice.exception.ProductNotUpdateException;
import com.ecommerce.productservice.util.ProductResponse;

@ControllerAdvice
public class ProductExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductResponse> handleProductyNotFoundException(ProductNotFoundException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
	
	@ExceptionHandler(ProductNotSaveException.class)
    public ResponseEntity<ProductResponse> handleProductNotSaveException(ProductNotSaveException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
	@ExceptionHandler(ProductNotUpdateException.class)
    public ResponseEntity<ProductResponse> handleAddressNotUpdateException(ProductNotUpdateException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProductResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return buildResponse(false, HttpStatus.BAD_REQUEST, "Validation Failed", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProductResponse> handleGeneralException(Exception ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
    
    private ResponseEntity<ProductResponse> buildResponse(boolean success, HttpStatus status, String message, Object data) {
    	ProductResponse response = new ProductResponse(success, status, message, data);
        return new ResponseEntity<>(response, status);
    }
	
}
