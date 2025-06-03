package com.ecommerce.addressservice.country.dao;

import java.util.List;

import com.ecommerce.addressservice.entity.Country;



public interface CountryDao {

	List<Country> getCountryList();

	Country getCountryById(Integer countryId);

	Integer saveCountry(Country country);

	Integer updateCountry(Country country);

	boolean deleteCountry(Integer countryId);

}
