package com.ecommerce.productservice.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.productservice.exception.SubCategoryNotDeleteException;
import com.ecommerce.productservice.exception.SubCategoryNotFoundException;
import com.ecommerce.productservice.exception.SubCategoryNotSaveException;
import com.ecommerce.productservice.exception.SubCategoryNotUpdateException;
import com.ecommerce.productservice.util.SubCategoryResponce;

@ControllerAdvice
public class SubCategoryExceptionHandler {

	@ExceptionHandler(SubCategoryNotFoundException.class)
    public ResponseEntity<SubCategoryResponce> handleSubCategoryNotFoundException(SubCategoryNotFoundException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
	
	@ExceptionHandler(SubCategoryNotSaveException.class)
    public ResponseEntity<SubCategoryResponce> handleSubCategoryNotSaveException(SubCategoryNotSaveException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
	@ExceptionHandler(SubCategoryNotUpdateException.class)
    public ResponseEntity<SubCategoryResponce> handleSubCategoryNotUpdateException(SubCategoryNotUpdateException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
	@ExceptionHandler(SubCategoryNotDeleteException.class)
    public ResponseEntity<SubCategoryResponce> handleSubCategoryNotDeleteException(SubCategoryNotDeleteException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<SubCategoryResponce> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return buildResponse(false, HttpStatus.BAD_REQUEST, "Validation Failed", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SubCategoryResponce> handleGeneralException(Exception ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
    
    private ResponseEntity<SubCategoryResponce> buildResponse(boolean success, HttpStatus status, String message, Object data) {
    	SubCategoryResponce response = new SubCategoryResponce(success, status, message, data);
        return new ResponseEntity<>(response, status);
    }
	
}
