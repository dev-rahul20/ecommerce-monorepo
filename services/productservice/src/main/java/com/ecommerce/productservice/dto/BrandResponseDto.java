package com.ecommerce.productservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
public class BrandResponseDto {
    
    private Integer id;
    private String name;
    private String description;
    private String logoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}