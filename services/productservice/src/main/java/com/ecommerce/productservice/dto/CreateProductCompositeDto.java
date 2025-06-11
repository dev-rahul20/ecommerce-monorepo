package com.ecommerce.productservice.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;



@Getter // Composite DTO holding nested DTOs
public class CreateProductCompositeDto {
	
	@NotNull
	@Valid
    private ProductRequestDto product;
	
	@NotEmpty // collection not null and size()>0
	@Valid
    private List<ProductImageRequestDto> images;
	
	@NotNull
	@Valid
    private ProductSpecificationRequestDto specifications;
    
}