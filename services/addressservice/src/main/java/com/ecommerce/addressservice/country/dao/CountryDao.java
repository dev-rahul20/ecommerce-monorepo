package com.ecommerce.addressservice.country.dao;

import java.util.List;

import com.ecommerce.addressservice.dto.CountryResponceDto;
import com.ecommerce.addressservice.entity.Country;



public interface CountryDao {

	List<CountryResponceDto> getCountryList();
	CountryResponceDto getCountryById(Integer countryId);
	Integer saveCountry(Country country);
	Integer updateCountry(Country country);
	Country getCountry(Integer countryId);
	boolean deleteCountry(Country country);

}
