package com.ecommerce.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
public class SupplierRequestDto {
    
	@NotBlank
    private String name;
	
	@NotBlank
    private String description;
	
	@NotBlank
    private String contactInfo;
}