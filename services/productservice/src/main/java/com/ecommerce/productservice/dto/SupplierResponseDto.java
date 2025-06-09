package com.ecommerce.productservice.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class SupplierResponseDto {
    private Integer id;
    private String name;
    private String description;
    private String contactInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}