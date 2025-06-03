package com.ecommerce.addressservice.address.dao;

import java.util.List;

import com.ecommerce.addressservice.entity.Address;

public interface AddressDao {

	List<Address> getAllAddress();
	
	Address getAddressByAddressId(Integer addressId);
	
	Address getAddressByAddressIdAndUserId(Integer addressId, Integer userId);
	
	Address getAddressByUserId(Integer usersId);

	Integer saveAddress(Address address);

	Integer updateAddress(Address address);

	Boolean deleteAddress(Integer addressId, Integer userId);

	
	
}
