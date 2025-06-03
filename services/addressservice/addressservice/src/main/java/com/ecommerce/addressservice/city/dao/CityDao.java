package com.ecommerce.addressservice.city.dao;

import java.util.List;

import com.ecommerce.addressservice.entity.City;


public interface CityDao {

	List<City> getCityList();

	City getCityById(Integer cityId);

	Integer saveCity(City city);

	Integer updateCity(City city);

	boolean deleteCity(Integer cityId);

}