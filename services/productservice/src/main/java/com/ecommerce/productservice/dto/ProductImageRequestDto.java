package com.ecommerce.productservice.dto;

import lombok.Getter;

@Getter
public class ProductImageRequestDto {
    private Integer productId;
    private String imageUrl;
    private Integer sortOrder;
}