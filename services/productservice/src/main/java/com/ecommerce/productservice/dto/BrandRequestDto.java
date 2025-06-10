package com.ecommerce.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
public class BrandRequestDto {
    
	@NotBlank
    private String name;
	
	@NotBlank
    private String description;
	
	@NotBlank
    private String logoUrl;
}