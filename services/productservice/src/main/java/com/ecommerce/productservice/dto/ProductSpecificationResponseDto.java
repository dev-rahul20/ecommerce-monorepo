package com.ecommerce.productservice.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
public class ProductSpecificationResponseDto {
    private Integer id;
    private Integer productId;
    private String specKey;
    private String specValue;
    private LocalDateTime createdAt;
}