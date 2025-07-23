package com.ecommerce.productservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
	

	private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer subCategoryId;
    private Integer brandId;
    private Integer supplierId;
    private BigDecimal averageRating;
    private Integer totalReviews;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // Optionally include nested details:
    private List<ProductImageResponseDto> productImages;
    private List<ProductSpecificationResponseDto> productSpecifications;
}