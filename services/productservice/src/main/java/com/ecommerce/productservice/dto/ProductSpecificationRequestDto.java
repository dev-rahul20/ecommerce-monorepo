package com.ecommerce.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
public class ProductSpecificationRequestDto {
    
	@NotBlank
    private String specKey;
	
	@NotBlank
    private String specValue;
}