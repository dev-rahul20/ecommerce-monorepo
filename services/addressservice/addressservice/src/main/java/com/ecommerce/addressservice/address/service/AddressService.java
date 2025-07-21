package com.ecommerce.addressservice.address.service;

import java.util.List;

import com.ecommerce.addressservice.dto.AddressRequestDto;
import com.ecommerce.addressservice.dto.AddressResponseDto;

public interface AddressService {

	List<AddressResponseDto> getAllAddress();
	
	AddressResponseDto getByAddressId(Integer addressId);
	
	List<AddressResponseDto> getByUserId(Integer userId);

	Integer saveAddress(AddressRequestDto dtoList);
	
	void saveAllAddress(List<AddressRequestDto> dtoList);
	
	Integer updateAddress(Integer addressId, AddressRequestDto address);

	Boolean deleteAddress(Integer addressId);

	Boolean deleteAddressByUserId(Integer userId);

}
