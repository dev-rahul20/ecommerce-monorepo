package com.ecommerce.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CategoryRequestDto {
    
	// Reference to the parent category
    @NotNull
	private Integer parentCategoryId; 
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String description;
}