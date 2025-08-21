package com.ecommerce.cartservice.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductImageResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer productId;
	private String imageUrl;
	private Integer sortOrder;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
