package com.ecommerce.productservice.dto;

import lombok.Getter;

@Getter
public class SubCategoryRequestDto {
    // Reference to the mid-level category
    private Integer categoryId;
    private String name;
    private String description;
}