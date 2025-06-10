package com.ecommerce.productservice.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.productservice.exception.BrandNotDeleteException;
import com.ecommerce.productservice.exception.BrandNotFoundException;
import com.ecommerce.productservice.exception.BrandNotSaveException;
import com.ecommerce.productservice.exception.BrandNotUpdateException;
import com.ecommerce.productservice.util.BrandResponce;

@ControllerAdvice
public class BrandExceptionHandler {

	@ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<BrandResponce> handleBrandyNotFoundException(BrandNotFoundException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
	
	@ExceptionHandler(BrandNotSaveException.class)
    public ResponseEntity<BrandResponce> handleBrandNotSaveException(BrandNotSaveException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
	@ExceptionHandler(BrandNotUpdateException.class)
    public ResponseEntity<BrandResponce> handleBrandNotUpdateException(BrandNotUpdateException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
	@ExceptionHandler(BrandNotDeleteException.class)
    public ResponseEntity<BrandResponce> handleBrandNotDeleteException(BrandNotDeleteException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BrandResponce> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return buildResponse(false, HttpStatus.BAD_REQUEST, "Validation Failed", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BrandResponce> handleGeneralException(Exception ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
    
    private ResponseEntity<BrandResponce> buildResponse(boolean success, HttpStatus status, String message, Object data) {
    	BrandResponce response = new BrandResponce(success, status, message, data);
        return new ResponseEntity<>(response, status);
    }
	
}
