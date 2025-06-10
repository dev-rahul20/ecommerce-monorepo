package com.ecommerce.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ParentCategoryRequestDto {
	
	@NotBlank
    private String name;
	
	@NotBlank
    private String description;
}