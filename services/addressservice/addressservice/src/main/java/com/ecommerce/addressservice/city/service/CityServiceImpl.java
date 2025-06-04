package com.ecommerce.addressservice.city.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.addressservice.city.dao.CityDao;
import com.ecommerce.addressservice.dto.CityResponceDto;
import com.ecommerce.addressservice.entity.City;
import com.ecommerce.addressservice.exception.CityNotFoundException;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class CityServiceImpl implements CityService {


    private final CityDao cityDao;

    @Override
    @Transactional(readOnly = true)
    public List<CityResponceDto> getCityList() {
        return cityDao.getCityList();
    }

    @Override
    @Transactional(readOnly = true)
    public CityResponceDto getCityById(Integer cityId) {
        
    	CityResponceDto city = cityDao.getCityById(cityId);
        
    	Optional.ofNullable(city).orElseThrow(() -> new CityNotFoundException("City with ID " + cityId + " not found"));
    	
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
        
        CityResponceDto existing = cityDao.getCityById(city.getCityId()); // Check if the city exists before updating
        
        Optional.ofNullable(existing).orElseThrow(() -> new CityNotFoundException("City with ID " + city.getCityId() + " not found for update"));
        
        return cityDao.updateCity(city);
    }

    @Override
    @Transactional
    public boolean deleteCity(Integer cityId) {
    	
    	CityResponceDto existing = cityDao.getCityById(cityId); // Check if the City exists before deletion
    	
    	Optional.ofNullable(existing).orElseThrow(() -> new CityNotFoundException("City with ID " + cityId + " not found for deletion"));
    	
        return cityDao.deleteCity(cityId);
    }
}
