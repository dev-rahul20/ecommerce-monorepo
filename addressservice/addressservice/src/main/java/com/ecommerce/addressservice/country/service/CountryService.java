package com.ecommerce.addressservice.country.service;

import java.util.List;

import com.ecommerce.addressservice.entity.Country;



public interface CountryService {

	List<Country> getCountryList();

	Country getCountryById(Integer countryId);

	Integer saveCountry(Country country);

	Integer updateCountry(Country country);

	boolean deleteCountry(Integer countryId);

}
