package com.ecommerce.addressservice.city.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.addressservice.city.service.CityService;
import com.ecommerce.addressservice.dto.CityResponceDto;
import com.ecommerce.addressservice.entity.City;
import com.ecommerce.addressservice.util.AddressResponse;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("city")
public class CityController {


    private final CityService service;

    @GetMapping("/get-city-list")
    public AddressResponse getCityList() {
        List<CityResponceDto> list = service.getCityList();
        return new AddressResponse(true, HttpStatus.OK, "Successfully fetched data", list);
    }

    @GetMapping("/get-city-by-id/{cityId}")
    public AddressResponse getCountryById(@PathVariable Integer cityId) {
    	CityResponceDto city = service.getCityById(cityId); // should throw CityNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "City found", city);
    }

    @PostMapping("/save-city")
    public AddressResponse saveState(@RequestBody City city) {
        Integer savedCityId = service.saveCity(city);
        return new AddressResponse(true, HttpStatus.CREATED, "City saved successfully", savedCityId);
    }

    @PostMapping("/update-city")
    public AddressResponse updateState(@RequestBody City city) {
        Integer updatedCityId = service.updateCity(city);
        return new AddressResponse(true, HttpStatus.OK, "City updated successfully", updatedCityId);
    }

    @DeleteMapping("/delete-city/{cityId}")
    public AddressResponse deleteState(@PathVariable Integer cityId) {
        service.deleteCity(cityId); // should throw CityNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "City deleted successfully", null);
    }
}
