package com.ecommerce.wishlistservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ProductDTO implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer subCategoryId;
    private Integer brandId;
    private Integer supplierId;
    private BigDecimal averageRating;
    private Integer totalReviews;

    // Optionally include nested details:
    private  List<ProductImageResponseDto> productImages;
    private  List<ProductSpecificationResponseDto> productSpecifications;
    
}