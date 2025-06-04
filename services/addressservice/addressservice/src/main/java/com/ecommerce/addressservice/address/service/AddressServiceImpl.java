package com.ecommerce.addressservice.address.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.addressservice.address.dao.AddressDao;
import com.ecommerce.addressservice.city.dao.CityDao;
import com.ecommerce.addressservice.country.dao.CountryDao;
import com.ecommerce.addressservice.dto.AddressRequestDto;
import com.ecommerce.addressservice.entity.Address;
import com.ecommerce.addressservice.entity.City;
import com.ecommerce.addressservice.entity.Country;
import com.ecommerce.addressservice.entity.State;
import com.ecommerce.addressservice.exception.AddressNotFoundException;
import com.ecommerce.addressservice.exception.AddressNotSaveException;
import com.ecommerce.addressservice.exception.AddressNotUpdateException;
import com.ecommerce.addressservice.exception.CityNotFoundException;
import com.ecommerce.addressservice.exception.CountryNotFoundException;
import com.ecommerce.addressservice.exception.StateNotFoundException;
import com.ecommerce.addressservice.state.dao.StateDao;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private StateDao stateDao;
	
	@Autowired
	private CountryDao countryDao;
	
	@Autowired
    private ModelMapper modelMapper;
	
	private City validateAndGetCity(Integer cityId) {
		
		City city = Optional.ofNullable(cityDao.getCityById(cityId))
							.orElseThrow(() -> new CityNotFoundException("City with Id : "+cityId+" not found"));
		return city;
	}
	
	private State validateAndGetState(Integer stateId) {
		
		State state = Optional.ofNullable(stateDao.getStateById(stateId))
							  .orElseThrow(() -> new StateNotFoundException("State with Id : "+stateId+" not found"));
		return state;
	}
	
	private Country validateAndGetCountry(Integer countryId) {
		
		Country country = Optional.ofNullable(countryDao.getCountryById(countryId))
							      .orElseThrow(() -> new CountryNotFoundException("Country with Id : "+countryId+" not found"));
		return country;
	}
	
	private Address populateAddressRelations(Address address, AddressRequestDto dto) {
		
		City    city    = validateAndGetCity(dto.getCityId());
        State   state   = validateAndGetState(dto.getStateId());
        Country country = validateAndGetCountry(dto.getCountryId());

        address.setCity(city);
        address.setState(state);
        address.setCountry(country);
        
        return address;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Address> getAllAddress() {
		return addressDao.getAllAddress();
	}

	@Override
	@Transactional(readOnly = true)
	public Address getAddressByAddressId(Integer addressId) {
		
		Address address = addressDao.getAddressByAddressId(addressId);
		
		if (address == null) {
            throw new AddressNotFoundException("Address with Address ID " + address + " not found");
        }
		return address;
	}

	@Override
	@Transactional(readOnly = true)
	public Address getAddressByUserId(Integer userId) {
		
		Address address = addressDao.getAddressByUserId(userId);
		
		if (address == null) {
            throw new AddressNotFoundException("Address with User ID " + address + " not found");
        }
		
		return address;
	}

	@Override
	@Transactional(readOnly = true)
	public Address getAddressByAddressIdAndUserId(Integer addressId, Integer userId) {
		
		Address address = addressDao.getAddressByAddressIdAndUserId(addressId, userId);
		
		if (address == null) {
            throw new AddressNotFoundException("Address not found");
        }
		return address;
	}
	
	@Override
	@Transactional
	public Integer saveAddress(AddressRequestDto dto) {
		
		Address address = modelMapper.map(dto, Address.class);
		
		populateAddressRelations(address, dto);
		
		Integer savedAddressId = addressDao.saveAddress(address);
		
		return Optional.ofNullable(savedAddressId).filter(id -> id > 0).orElseThrow(() -> new AddressNotSaveException("Error while saving address"));

	}

	@Override
	@Transactional
	public Integer updateAddress(AddressRequestDto dto) {
		
		Address address = modelMapper.map(dto, Address.class);
		
		populateAddressRelations(address, dto);

		Integer updatedAddressId = addressDao.updateAddress(address);
		
		return Optional.ofNullable(updatedAddressId).filter(id -> id > 0).orElseThrow(() -> new AddressNotUpdateException("Error while updating address"));
	}

	@Override
	@Transactional
	public Boolean deleteAddress(Integer addressId, Integer userId) {
		Address existing = addressDao.getAddressByAddressIdAndUserId(addressId, userId);
        if (existing == null) {
            throw new AddressNotFoundException("Address not found for deletion");
        }
        return addressDao.deleteAddress(addressId, userId);
	}

	
	
}
