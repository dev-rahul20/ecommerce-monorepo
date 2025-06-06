package com.ecommerce.addressservice.country.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.addressservice.country.dao.CountryDao;
import com.ecommerce.addressservice.dto.CountryRequestDto;
import com.ecommerce.addressservice.dto.CountryResponceDto;
import com.ecommerce.addressservice.entity.Country;
import com.ecommerce.addressservice.exception.CountryNotFoundException;

import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {


    private final CountryDao dao;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CountryResponceDto> getCountryList() {
        return dao.getCountryList();
    }

    @Override
    @Transactional(readOnly = true)
    public CountryResponceDto getCountryById(Integer countryId) {
    	
    	CountryResponceDto country = dao.getCountryById(countryId);
        
    	Optional.ofNullable(country).orElseThrow(() -> new CountryNotFoundException("Country with ID " + countryId + " not found"));
    	
        return country;
    }

    @Override
    @Transactional
    public Integer saveCountry(CountryRequestDto dto) {
    	
    	Country country = modelMapper.map(dto, Country.class);
    	
        return dao.saveCountry(country);
    }

    @Override
    @Transactional
    public Integer updateCountry(Integer countryId, CountryRequestDto dto) {
        // Check if the country exists before updating
    	CountryResponceDto existing = dao.getCountryById(dto.getCountryId());
    	
    	Optional.ofNullable(existing).orElseThrow(() -> new CountryNotFoundException("Country with ID " + dto.getCountryId() + " not found for update"));
    	
    	Country country = modelMapper.map(existing, Country.class);
    	
    	country.setCountryId(countryId);
    	
        return dao.updateCountry(country);
    }

    @Override
    @Transactional
    public boolean deleteCountry(Integer countryId) {
    	
    	Country country = dao.getCountry(countryId);
  
    	Optional.ofNullable(country).orElseThrow(() -> new CountryNotFoundException("Country with ID " + countryId + " not found for deletion"));
    	
        return dao.deleteCountry(country);
    }
}
