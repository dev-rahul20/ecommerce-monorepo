package com.ecommerce.productservice.dto;

import lombok.Getter;

@Getter
public class CategoryRequestDto {
    // Reference to the parent category
    private Integer parentCategoryId; 
    private String name;
    private String description;
}