package com.ecommerce.addressservice.country.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.addressservice.country.service.CountryService;
import com.ecommerce.addressservice.entity.Country;
import com.ecommerce.addressservice.util.AddressResponse;

import lombok.AllArgsConstructor;



@RestController
@AllArgsConstructor
@RequestMapping("country")
public class CountryController {

    private final CountryService service;

    @GetMapping("/get-country-list")
    public AddressResponse getCountryList() {
        List<Country> list = service.getCountryList();
        return new AddressResponse(true, HttpStatus.OK, "Successfully fetched data", list);
    }

    @GetMapping("/get-country-by-id/{countryId}")
    public AddressResponse getCountryById(@PathVariable Integer countryId) {
        Country country = service.getCountryById(countryId); // should throw CountryNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "Country found", country);
    }

    @PostMapping("/save-country")
    public AddressResponse saveCountry(@RequestBody Country country) {
        Integer savedCountryId = service.saveCountry(country);
        return new AddressResponse(true, HttpStatus.CREATED, "Country saved successfully", savedCountryId);
    }

    @PostMapping("/update-country")
    public AddressResponse updateCountry(@RequestBody Country country) {
        Integer updatedCountryId = service.updateCountry(country);
        return new AddressResponse(true, HttpStatus.OK, "Country updated successfully", updatedCountryId);
    }

    @DeleteMapping("/delete-country/{countryId}")
    public AddressResponse deleteCountry(@PathVariable Integer countryId) {
        service.deleteCountry(countryId); // should throw CountryNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "Country deleted successfully", null);
    }
}
