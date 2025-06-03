package com.ecommerce.addressservice.city.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.addressservice.city.dao.CityDao;
import com.ecommerce.addressservice.entity.City;
import com.ecommerce.addressservice.exception.CityNotFoundException;



@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Override
    @Transactional(readOnly = true)
    public List<City> getCityList() {
        return cityDao.getCityList();
    }

    @Override
    @Transactional(readOnly = true)
    public City getCityById(Integer cityId) {
        City city = cityDao.getCityById(cityId);
        if (city == null) {
            throw new CityNotFoundException("State with ID " + cityId + " not found");
        }
        return city;
    }

    @Override
    @Transactional
    public Integer saveCity(City city) {
        return cityDao.saveCity(city);
    }

    @Override
    @Transactional
    public Integer updateCity(City city) {
        // Check if the city exists before updating
        City existing = cityDao.getCityById(city.getCityId());
        if (existing == null) {
            throw new CityNotFoundException("City with ID " + city.getCityId() + " not found for update");
        }
        return cityDao.updateCity(city);
    }

    @Override
    @Transactional
    public boolean deleteCity(Integer cityId) {
    	// Check if the City exists before deletion
    	City existing = cityDao.getCityById(cityId);
        if (existing == null) {
            throw new CityNotFoundException("City with ID " + cityId + " not found for deletion");
        }
        return cityDao.deleteCity(cityId);
    }
}
