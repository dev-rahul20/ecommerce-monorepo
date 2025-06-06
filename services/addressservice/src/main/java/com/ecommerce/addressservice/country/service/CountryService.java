package com.ecommerce.addressservice.country.service;

import java.util.List;

import com.ecommerce.addressservice.dto.CountryRequestDto;
import com.ecommerce.addressservice.dto.CountryResponceDto;



public interface CountryService {

	List<CountryResponceDto> getCountryList();

	CountryResponceDto getCountryById(Integer countryId);

	Integer saveCountry(CountryRequestDto dto);

	Integer updateCountry(Integer countryId, CountryRequestDto dto);

	boolean deleteCountry(Integer countryId);

}
