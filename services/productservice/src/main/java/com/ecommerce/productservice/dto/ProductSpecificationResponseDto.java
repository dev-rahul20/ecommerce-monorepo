package com.ecommerce.productservice.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ProductSpecificationResponseDto {
    private Integer id;
    private Integer productId;
    private String specKey;
    private String specValue;
    private LocalDateTime createdAt;
}