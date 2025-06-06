package com.ecommerce.addressservice.country.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.addressservice.country.service.CountryService;
import com.ecommerce.addressservice.dto.CountryRequestDto;
import com.ecommerce.addressservice.dto.CountryResponceDto;
import com.ecommerce.addressservice.util.AddressResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("country")
public class CountryController {

    private final CountryService service;

    @GetMapping("get-country-list")
    public AddressResponse getCountryList() {
        List<CountryResponceDto> list = service.getCountryList();
        return new AddressResponse(true, HttpStatus.OK, "Successfully fetched data", list);
    }

    @GetMapping("/get-country-by-id/{countryId}")
    public AddressResponse getCountryById(@PathVariable @NotNull @Positive Integer countryId) {
    	CountryResponceDto country = service.getCountryById(countryId); // should throw CountryNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "Country found", country);
    }

    @PostMapping("/save-country")
    public AddressResponse saveCountry(@RequestBody @Valid CountryRequestDto country) {
        Integer savedCountryId = service.saveCountry(country);
        return new AddressResponse(true, HttpStatus.CREATED, "Country saved successfully", savedCountryId);
    }

    @PutMapping("/update-country/{countryId}")
    public AddressResponse updateCountry(@PathVariable @NotNull @Positive Integer countryId, @RequestBody @Valid CountryRequestDto country) {
        Integer updatedCountryId = service.updateCountry(countryId, country);
        return new AddressResponse(true, HttpStatus.OK, "Country updated successfully", updatedCountryId);
    }

    @DeleteMapping("/delete-country/{countryId}")
    public AddressResponse deleteCountry(@PathVariable @NotNull @Positive Integer countryId) {
        service.deleteCountry(countryId); // should throw CountryNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "Country deleted successfully", null);
    }
}
