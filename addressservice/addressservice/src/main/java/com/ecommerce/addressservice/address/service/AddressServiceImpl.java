package com.ecommerce.addressservice.address.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.addressservice.address.dao.AddressDao;
import com.ecommerce.addressservice.entity.Address;
import com.ecommerce.addressservice.exception.AddressNotFoundException;
import com.ecommerce.addressservice.exception.AddressNotSaveException;
import com.ecommerce.addressservice.exception.AddressNotUpdateException;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao dao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Address> getAllAddress() {
		return dao.getAllAddress();
	}

	@Override
	@Transactional(readOnly = true)
	public Address getAddressByAddressId(Integer addressId) {
		
		Address address = dao.getAddressByAddressId(addressId);
		
		if (address == null) {
            throw new AddressNotFoundException("Address with Address ID " + address + " not found");
        }
		return address;
	}

	@Override
	@Transactional(readOnly = true)
	public Address getAddressByUserId(Integer userId) {
		
		Address address = dao.getAddressByUserId(userId);
		
		if (address == null) {
            throw new AddressNotFoundException("Address with User ID " + address + " not found");
        }
		
		return address;
	}

	@Override
	@Transactional(readOnly = true)
	public Address getAddressByAddressIdAndUserId(Integer addressId, Integer userId) {
		
		Address address = dao.getAddressByAddressIdAndUserId(addressId, userId);
		
		if (address == null) {
            throw new AddressNotFoundException("Address not found");
        }
		return address;
	}
	
	@Override
	@Transactional
	public Integer saveAddress(Address address) {
		
		Integer savedAddressId = dao.saveAddress(address);
		
		if (savedAddressId <= 0 || savedAddressId == null) {
            throw new AddressNotSaveException("Error while saving address");
        }
		return savedAddressId;
	}

	@Override
	@Transactional
	public Integer updateAddress(Address address) {
		
		Integer updatedAddressId = dao.updateAddress(address);
		
		if (updatedAddressId <= 0 || updatedAddressId == null) {
            throw new AddressNotUpdateException("Error while updating address");
        }
		return updatedAddressId;
	}

	@Override
	@Transactional
	public Boolean deleteAddress(Integer addressId, Integer userId) {
		Address existing = dao.getAddressByAddressIdAndUserId(addressId, userId);
        if (existing == null) {
            throw new AddressNotFoundException("Address not found for deletion");
        }
        return dao.deleteAddress(addressId, userId);
	}

	
	
}
