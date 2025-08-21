package com.ecommerce.cartservice.dto;

import lombok.Data;

@Data
public class ProductResponseWrapper {

	private boolean success;
	private String status;
	private String message;
	private ProductDTO data;

}
