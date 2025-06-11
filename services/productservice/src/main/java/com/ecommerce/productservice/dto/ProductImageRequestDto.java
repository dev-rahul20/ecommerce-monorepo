package com.ecommerce.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class ProductImageRequestDto {
	
	@NotBlank
    private String imageAaBase64String;
	
	@NotBlank
    private String fileName;
	
	@NotNull
	@PositiveOrZero
    private Integer sortOrder;
}