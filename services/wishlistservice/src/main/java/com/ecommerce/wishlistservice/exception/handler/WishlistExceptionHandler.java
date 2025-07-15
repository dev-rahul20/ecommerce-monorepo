package com.ecommerce.wishlistservice.exception.handler;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.wishlistservice.exception.WishListItemAlreadyExistException;
import com.ecommerce.wishlistservice.exception.WishListItemNotDeleteException;
import com.ecommerce.wishlistservice.exception.WishListItemNotSaveException;
import com.ecommerce.wishlistservice.util.WishlistResponce;

@ControllerAdvice
public class WishlistExceptionHandler {

	@ExceptionHandler(WishListItemNotSaveException.class)
    public ResponseEntity<WishlistResponce> handleWishListItemNotSaveException(WishListItemNotSaveException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

	@ExceptionHandler(WishListItemNotDeleteException.class)
    public ResponseEntity<WishlistResponce> handleWishListItemNotDeleteException(WishListItemNotDeleteException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
	 
	@ExceptionHandler(WishListItemAlreadyExistException.class)
    public ResponseEntity<WishlistResponce> handleWishListItemAlreadyExistException(WishListItemAlreadyExistException ex) {
        return buildResponse(false, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WishlistResponce> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return buildResponse(false, HttpStatus.BAD_REQUEST, "Validation Failed", errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WishlistResponce> handleGeneralException(Exception ex) {
        return buildResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }

    private ResponseEntity<WishlistResponce> buildResponse(boolean success, HttpStatus status, String message, Object data) {
    	WishlistResponce response = new WishlistResponce(success, status, message, data);
        return new ResponseEntity<>(response, status);
    }
}
