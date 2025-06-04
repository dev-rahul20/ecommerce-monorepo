package com.ecommerce.addressservice.address.service;

import java.util.List;

import com.ecommerce.addressservice.dto.AddressRequestDto;
import com.ecommerce.addressservice.entity.Address;

public interface AddressService {

	List<Address> getAllAddress();
	
	Address getAddressByAddressId(Integer addressId);
	
	Address getAddressByUserId(Integer usersId);
	
	Address getAddressByAddressIdAndUserId(Integer addressId, Integer userId);

	Integer saveAddress(AddressRequestDto address);

	Integer updateAddress(AddressRequestDto address);

	Boolean deleteAddress(Integer addressId, Integer userId);

	
	
	
}
