package com.ecommerce.productservice.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductRequestDto {
    
	@NotBlank
	private String name;
    
	@NotBlank
	private String description;
    
	@NotNull
	private BigDecimal price;
	
	@NotNull
    private Integer subCategoryId;
	
	@NotNull
    private Integer brandId;    
	
	@NotNull
    private Integer supplierId;
}