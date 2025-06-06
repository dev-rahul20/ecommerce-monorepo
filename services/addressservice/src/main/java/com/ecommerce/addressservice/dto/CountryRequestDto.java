package com.ecommerce.addressservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryRequestDto {

	private Integer countryId;
	
	@NotBlank
    private String countryCode;
    
	@NotBlank
	private String countryName;
	
}
