package com.ecommerce.addressservice.city.service;

import java.util.List;

import com.ecommerce.addressservice.dto.CityResponceDto;
import com.ecommerce.addressservice.entity.City;


public interface CityService {

	List<CityResponceDto> getCityList();

	CityResponceDto getCityById(Integer cityId);

	Integer saveCity(City city);

	Integer updateCity(City city);

	boolean deleteCity(Integer cityId);

}
