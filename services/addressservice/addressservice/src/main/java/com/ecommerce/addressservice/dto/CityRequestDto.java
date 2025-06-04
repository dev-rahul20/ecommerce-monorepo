package com.ecommerce.addressservice.dto;

import com.ecommerce.addressservice.entity.State;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityRequestDto {

	private Integer cityId;
    private String cityName;
    private State stateId;
	
}
