package com.ecommerce.productservice.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class SubCategoryResponseDto {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}