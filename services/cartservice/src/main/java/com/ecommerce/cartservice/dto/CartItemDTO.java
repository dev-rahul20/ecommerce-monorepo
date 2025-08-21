package com.ecommerce.cartservice.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CartItemDTO {
	
	private String cartItemId;
	private Integer userId;
	private ProductDTO product;
	private Integer quantity;
	private LocalDateTime addedAt;

}
