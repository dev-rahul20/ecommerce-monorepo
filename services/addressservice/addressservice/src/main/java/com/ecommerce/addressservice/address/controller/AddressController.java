package com.ecommerce.addressservice.address.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.addressservice.address.service.AddressService;
import com.ecommerce.addressservice.dto.AddressRequestDto;
import com.ecommerce.addressservice.entity.Address;
import com.ecommerce.addressservice.util.AddressResponse;
import com.ecommerce.addressservice.validation.OnCreateValidation;
import com.ecommerce.addressservice.validation.OnUpdateValidation;


@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService service;

    @GetMapping("/get-all-address")
    public AddressResponse getAllAddress() {
        List<Address> list = service.getAllAddress();
        return new AddressResponse(true, HttpStatus.OK, "Successfully fetched data", list);
    }

    @GetMapping("/get-address-by-id/{addressId}")
    public AddressResponse getCountryById(@PathVariable Integer addressId) {
        Address address = service.getAddressByAddressId(addressId); // should throw AddressNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "Address found", address);
    }

    @PostMapping("/save-address")
    public AddressResponse saveAddress(@RequestBody @Validated(OnCreateValidation.class) AddressRequestDto address) {
        Integer savedAddressId = service.saveAddress(address);
        return new AddressResponse(true, HttpStatus.CREATED, "Address saved successfully", savedAddressId);
    }

    @PostMapping("/update-address")
    public AddressResponse updateAddress(@RequestBody @Validated(OnUpdateValidation.class) AddressRequestDto address) {
        Integer updatedAddressId = service.updateAddress(address);
        return new AddressResponse(true, HttpStatus.OK, "Address updated successfully", updatedAddressId);
    }

    @DeleteMapping("/delete-address/{addressId}/{userId}")
    public AddressResponse deleteState(@PathVariable Integer addressId, @PathVariable Integer userId) {
        service.deleteAddress(addressId, userId); // should throw AddressNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "Address deleted successfully", null);
    }
}
