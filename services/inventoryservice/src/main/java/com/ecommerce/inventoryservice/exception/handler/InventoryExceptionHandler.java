package com.ecommerce.inventoryservice.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.inventoryservice.exception.InventoryItemNotDeleteException;
import com.ecommerce.inventoryservice.exception.InventoryItemNotFoundException;
import com.ecommerce.inventoryservice.exception.InventoryItemNotSaveException;
import com.ecommerce.inventoryservice.exception.InventoryItemNotUpdateException;
import com.ecommerce.inventoryservice.util.InventoryResponce;



@ControllerAdvice
public class InventoryExceptionHandler {

	@ExceptionHandler(InventoryItemNotSaveException.class)
    public ResponseEntity<InventoryResponce> handleInventoryItemNotSaveException(InventoryItemNotSaveException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
	
	@ExceptionHandler(InventoryItemNotUpdateException.class)
    public ResponseEntity<InventoryResponce> handleInventoryItemNotUpdateException(InventoryItemNotUpdateException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
	@ExceptionHandler(InventoryItemNotDeleteException.class)
    public ResponseEntity<InventoryResponce> handleInventoryItemNotDeleteException(InventoryItemNotDeleteException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
	@ExceptionHandler(InventoryItemNotFoundException.class)
    public ResponseEntity<InventoryResponce> handleInventoryItemNotFoundException(InventoryItemNotFoundException ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InventoryResponce> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return buildResponse(false, HttpStatus.BAD_REQUEST, "Validation Failed", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<InventoryResponce> handleGeneralException(Exception ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
    
    private ResponseEntity<InventoryResponce> buildResponse(boolean success, HttpStatus status, String message, Object data) {
    	InventoryResponce response = new InventoryResponce(success, status, message, data);
        return new ResponseEntity<>(response, status);
    }
	
}
