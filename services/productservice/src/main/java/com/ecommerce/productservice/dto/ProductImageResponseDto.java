package com.ecommerce.productservice.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ProductImageResponseDto {
    private Integer id;
    private Integer productId;
    private String imageUrl;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}