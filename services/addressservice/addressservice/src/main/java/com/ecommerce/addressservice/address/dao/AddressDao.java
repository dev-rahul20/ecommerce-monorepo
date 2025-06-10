package com.ecommerce.addressservice.address.dao;

import java.util.List;

import com.ecommerce.addressservice.dto.AddressResponceDto;
import com.ecommerce.addressservice.entity.Address;

public interface AddressDao {

	List<AddressResponceDto> getAllAddress();
	
	Address getByAddressId(Integer addressId);
	
	List<AddressResponceDto> getByUserId(Integer userId);

	Integer saveAddress(Address address);

	Integer updateAddress(Address address);

	Boolean deleteAddress(Address address);

	Boolean deleteAddressByUserId(Integer userId);
	
}
