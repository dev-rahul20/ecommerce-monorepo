package com.ecommerce.addressservice.dto;

import com.ecommerce.addressservice.entity.Country;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StateRequestDto {

    private Integer stateId;
    
    @NotBlank
    private String stateName;
    
    @NotBlank
    private Country country;
	
}
