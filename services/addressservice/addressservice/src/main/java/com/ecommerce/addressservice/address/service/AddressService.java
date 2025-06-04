package com.ecommerce.addressservice.address.service;

import java.util.List;

import com.ecommerce.addressservice.dto.AddressRequestDto;
import com.ecommerce.addressservice.dto.AddressResponceDto;

public interface AddressService {

	List<AddressResponceDto> getAllAddress();
	
	AddressResponceDto getByAddressId(Integer addressId);

	Integer saveAddress(AddressRequestDto address);

	Integer updateAddress(Integer addressId, AddressRequestDto address);

	Boolean deleteAddress(Integer addressId);
	
}
