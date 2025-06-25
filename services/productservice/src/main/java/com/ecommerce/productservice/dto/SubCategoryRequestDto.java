package com.ecommerce.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class SubCategoryRequestDto {
    
	// Reference to the mid-level category
    @NotNull
    @Positive
	private Integer categoryId;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String description;
}