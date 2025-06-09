package com.ecommerce.productservice.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CategoryResponseDto {
    private Integer id;
    private Integer parentCategoryId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}