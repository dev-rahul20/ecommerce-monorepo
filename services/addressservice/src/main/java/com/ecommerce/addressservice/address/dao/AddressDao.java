package com.ecommerce.addressservice.address.dao;

import java.util.List;

import com.ecommerce.addressservice.dto.AddressResponceDto;
import com.ecommerce.addressservice.entity.Address;

public interface AddressDao {

	List<AddressResponceDto> getAllAddress();
	
	AddressResponceDto getByAddressId(Integer addressId);
	
	List<AddressResponceDto> getByUserId(Integer userId);

	Integer saveAddress(Address address);

	Integer updateAddress(Address address);

	Boolean deleteAddress(Integer addressId);

	Boolean deleteAddressByUserId(Integer userId);
	
}
