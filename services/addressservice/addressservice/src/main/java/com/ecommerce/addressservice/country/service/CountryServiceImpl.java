package com.ecommerce.addressservice.country.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.addressservice.country.dao.CountryDao;
import com.ecommerce.addressservice.entity.Country;
import com.ecommerce.addressservice.exception.CountryNotFoundException;

import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {


    private final CountryDao dao;

    @Override
    @Transactional(readOnly = true)
    public List<Country> getCountryList() {
        return dao.getCountryList();
    }

    @Override
    @Transactional(readOnly = true)
    public Country getCountryById(Integer countryId) {
        Country country = dao.getCountryById(countryId);
        if (country == null) {
            throw new CountryNotFoundException("Country with ID " + countryId + " not found");
        }
        return country;
    }

    @Override
    @Transactional
    public Integer saveCountry(Country country) {
        return dao.saveCountry(country);
    }

    @Override
    @Transactional
    public Integer updateCountry(Country country) {
        // Check if the country exists before updating
        Country existing = dao.getCountryById(country.getCountryId());
        if (existing == null) {
            throw new CountryNotFoundException("Country with ID " + country.getCountryId() + " not found for update");
        }
        return dao.updateCountry(country);
    }

    @Override
    @Transactional
    public boolean deleteCountry(Integer countryId) {
        Country existing = dao.getCountryById(countryId);
        if (existing == null) {
            throw new CountryNotFoundException("Country with ID " + countryId + " not found for deletion");
        }
        return dao.deleteCountry(countryId);
    }
}
