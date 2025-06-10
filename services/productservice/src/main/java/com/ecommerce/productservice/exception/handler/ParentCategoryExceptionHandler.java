package com.ecommerce.productservice.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.productservice.exception.ParentCategoryNotDeleteException;
import com.ecommerce.productservice.exception.ParentCategoryNotFoundException;
import com.ecommerce.productservice.exception.ParentCategoryNotSaveException;
import com.ecommerce.productservice.exception.ParentCategoryNotUpdateException;
import com.ecommerce.productservice.util.ParentCategoryResponce;

@ControllerAdvice
public class ParentCategoryExceptionHandler {

	@ExceptionHandler(ParentCategoryNotFoundException.class)
	public ResponseEntity<ParentCategoryResponce> handleParentCategoryNotFoundException(ParentCategoryNotFoundException ex) {
		return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
	}
	
	@ExceptionHandler(ParentCategoryNotSaveException.class)
	public ResponseEntity<ParentCategoryResponce> handleParentCategoryNotSaveException(ParentCategoryNotSaveException ex) {
		return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
	}

	@ExceptionHandler(ParentCategoryNotUpdateException.class)
	public ResponseEntity<ParentCategoryResponce> handleParentCategoryNotUpdateException(ParentCategoryNotUpdateException ex) {
		return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
	}
	
	@ExceptionHandler(ParentCategoryNotDeleteException.class)
	public ResponseEntity<ParentCategoryResponce> handleParentCategoryNotDeleteException(ParentCategoryNotDeleteException ex) {
		return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
	}
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ParentCategoryResponce> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return buildResponse(false, HttpStatus.BAD_REQUEST, "Validation Failed", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ParentCategoryResponce> handleGeneralException(Exception ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
    
    private ResponseEntity<ParentCategoryResponce> buildResponse(boolean success, HttpStatus status, String message, Object data) {
    	ParentCategoryResponce response = new ParentCategoryResponce(success, status, message, data);
        return new ResponseEntity<>(response, status);
    }
}
