package com.ecommerce.productservice.dto;

import lombok.Getter;

@Getter
public class ProductSpecificationRequestDto {
    private Integer productId;
    private String specKey;
    private String specValue;
}