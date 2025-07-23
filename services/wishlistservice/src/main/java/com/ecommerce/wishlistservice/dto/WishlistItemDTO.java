package com.ecommerce.wishlistservice.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WishlistItemDTO {
    
	private String wishlistId;
    private Integer userId;
    private ProductDTO product;
    private LocalDateTime addedAt;
    
}