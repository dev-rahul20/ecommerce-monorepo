package com.ecommerce.addressservice.address.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.addressservice.address.service.AddressService;
import com.ecommerce.addressservice.dto.AddressRequestDto;
import com.ecommerce.addressservice.dto.AddressResponceDto;
import com.ecommerce.addressservice.util.AddressResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("addresses")
public class AddressController {

    private final AddressService service;

    @GetMapping("get-all")
    public AddressResponse getAllAddress() {
        List<AddressResponceDto> list = service.getAllAddress();
        return new AddressResponse(true, HttpStatus.OK, "Successfully fetched data", list);
    }

    @GetMapping("get-by-address-id/{addressId}")
    public AddressResponse getAddressyByAddressId(@PathVariable @NotNull @Positive Integer addressId) {
        AddressResponceDto address = service.getByAddressId(addressId); // should throw AddressNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "Address found", address);
    }

    @GetMapping("get-by-user-id/{userId}")
    public AddressResponse getByUserId(@PathVariable @NotNull @Positive Integer userId) {
        List<AddressResponceDto> addressList = service.getByUserId(userId); // should throw AddressNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "Address found", addressList);
    }
    
    @PostMapping("save")
    public AddressResponse saveAddress(@RequestBody @Valid AddressRequestDto address) {
        Integer savedAddressIds = service.saveAddress(address);
        return new AddressResponse(true, HttpStatus.CREATED, "Address saved successfully", savedAddressIds);
    }
    
    @PostMapping("save-all")
    public AddressResponse saveAllAddress(@RequestBody @Valid List<AddressRequestDto> address) {
        service.saveAllAddress(address);
        return new AddressResponse(true, HttpStatus.CREATED, "Address saved successfully");
    }

    @PutMapping("update/{addressId}")
    public AddressResponse updateAddress(@PathVariable @Valid @Positive Integer addressId, @RequestBody @Valid AddressRequestDto address) {
        Integer updatedAddressId = service.updateAddress(addressId, address);
        return new AddressResponse(true, HttpStatus.OK, "Address updated successfully", updatedAddressId);
    }

    @DeleteMapping("delete-by-address-id/{addressId}")
    public AddressResponse deleteAddress(@PathVariable @NotNull @Positive Integer addressId) {
        service.deleteAddress(addressId); // should throw AddressNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "Address deleted successfully", null);
    }
    
    @DeleteMapping("delete-by-user-id/{userId}")
    public AddressResponse deleteAddressByUserId(@PathVariable @NotNull @Positive Integer userId) {
        service.deleteAddress(userId); // should throw AddressNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "Address deleted successfully", null);
    }
}
