package com.ecommerce.wishlistservice.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageResponseDto implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private Integer id;
    private Integer productId;
    private String imageUrl;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}