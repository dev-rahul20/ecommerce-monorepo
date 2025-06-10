package com.ecommerce.productservice.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.productservice.exception.CategoryNotDeleteException;
import com.ecommerce.productservice.exception.CategoryNotFoundException;
import com.ecommerce.productservice.exception.CategoryNotSaveException;
import com.ecommerce.productservice.exception.CategoryNotUpdateException;
import com.ecommerce.productservice.util.CategoryResponce;

@ControllerAdvice
public class CategoryExceptionHandler {

	@ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<CategoryResponce> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
	
	@ExceptionHandler(CategoryNotSaveException.class)
    public ResponseEntity<CategoryResponce> handleCategoryNotSaveException(CategoryNotSaveException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
	@ExceptionHandler(CategoryNotUpdateException.class)
    public ResponseEntity<CategoryResponce> handleCategoryNotUpdateException(CategoryNotUpdateException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
	@ExceptionHandler(CategoryNotDeleteException.class)
    public ResponseEntity<CategoryResponce> handleCategoryNotDeleteException(CategoryNotDeleteException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CategoryResponce> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return buildResponse(false, HttpStatus.BAD_REQUEST, "Validation Failed", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CategoryResponce> handleGeneralException(Exception ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
    
    private ResponseEntity<CategoryResponce> buildResponse(boolean success, HttpStatus status, String message, Object data) {
    	CategoryResponce response = new CategoryResponce(success, status, message, data);
        return new ResponseEntity<>(response, status);
    }
	
}
