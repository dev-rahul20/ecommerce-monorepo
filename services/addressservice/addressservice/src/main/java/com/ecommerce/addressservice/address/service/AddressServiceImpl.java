package com.ecommerce.addressservice.address.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.addressservice.address.dao.AddressDao;
import com.ecommerce.addressservice.city.dao.CityDao;
import com.ecommerce.addressservice.country.dao.CountryDao;
import com.ecommerce.addressservice.dto.AddressRequestDto;
import com.ecommerce.addressservice.dto.AddressResponseDto;
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
import com.ecommerce.addressservice.exception.UserNotFoundException;
import com.ecommerce.addressservice.feign.UserClient;
import com.ecommerce.addressservice.state.dao.StateDao;
import com.ecommerce.addressservice.util.UserResponse;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
	
	private final AddressDao addressDao;
	private final UserClient userClient;
	private final CityDao cityDao;
	private final StateDao stateDao;
	private final CountryDao countryDao;
    private final ModelMapper modelMapper;
	
	private City validateAndGetCity(Integer cityId) {
		
		return Optional.ofNullable(cityDao.getCity(cityId))
					   .orElseThrow(() -> new CityNotFoundException("City with Id : " + cityId + " not found"));
	}
	
	private State validateAndGetState(Integer stateId) {
		
		return Optional.ofNullable(stateDao.getState(stateId))
					   .orElseThrow(() -> new StateNotFoundException("State with Id: " + stateId + " not found"));
	}
	
	private Country validateAndGetCountry(Integer countryId) {
		
		return Optional.ofNullable(countryDao.getCountry(countryId))
					   .orElseThrow(() -> new CountryNotFoundException("Country with Id: " + countryId + "not found"));
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
	public List<AddressResponseDto> getAllAddress() {
		return addressDao.getAllAddress();
	}

	@Override
	@Transactional(readOnly = true)
	public AddressResponseDto getByAddressId(Integer addressId) {
		
		Address address = addressDao.getByAddressId(addressId);
		
		Optional.ofNullable(address).orElseThrow(() -> new AddressNotFoundException("Address not found"));
		
		return modelMapper.map(address, AddressResponseDto.class);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AddressResponseDto> getByUserId(Integer userId) {
		
		List<AddressResponseDto> address = addressDao.getByUserId(userId);
		
		if (address == null || address.isEmpty()) {
		    throw new AddressNotFoundException("No address found for user with ID: " + userId);
		}
		
		return address;
	}
	
	@Override
	@Transactional
	public Integer saveAddress(AddressRequestDto dto) {
		
		try {
			userClient.getUserByUserId(dto.getUserId());
		} catch (FeignException.NotFound e) {
			throw new UserNotFoundException("User Not Found");
		}
		
		Address address = modelMapper.map(dto, Address.class);
			
		populateAddressRelations(address, dto);
					
		Integer savedAddressId = addressDao.saveAddress(address);
			
		Optional.ofNullable(savedAddressId)
				.filter(id -> id > 0)
				.orElseThrow(() -> new AddressNotSaveException("Error while saving address"));
	
		return savedAddressId;
	}
	
	@Override
	@Transactional
	public void saveAllAddress(List<AddressRequestDto> dtoList) {
		
		for(AddressRequestDto dto : dtoList) {
			
			try {
				userClient.getUserByUserId(dto.getUserId());
			} catch (FeignException.NotFound e) {
				throw new UserNotFoundException("User Not Found");
			}
			
			Address address = modelMapper.map(dto, Address.class);
			
			populateAddressRelations(address, dto);
			
			Integer savedAddressId = addressDao.saveAddress(address);
			
			Optional.ofNullable(savedAddressId)
					.filter(id -> id > 0)
					.orElseThrow(() -> new AddressNotSaveException("Error while saving address"));
		}
	}
	
	@Override
	@Transactional
	public Integer updateAddress(Integer addressId, AddressRequestDto dto) {
		
		Address existingAddress = addressDao.getByAddressId(addressId);
		
		Optional.ofNullable(existingAddress)
				.orElseThrow(() -> new AddressNotFoundException("Address with id : "+addressId+" not found"));
		
		Address address = modelMapper.map(dto, Address.class);
		
		address.setAdrId(addressId);
		
		populateAddressRelations(address, dto);

		Integer updatedAddressId = addressDao.updateAddress(address);
		
		return Optional.ofNullable(updatedAddressId)
					   .filter(id -> id > 0)
					   .orElseThrow(() -> new AddressNotUpdateException("Error while updating address"));
	}

	@Override
	@Transactional
	public Boolean deleteAddress(Integer addressId) {
		
		Address address = addressDao.getByAddressId(addressId);
		
		Optional.ofNullable(address)
				.orElseThrow(() -> new AddressNotFoundException("Address with id : "+addressId+" not found for deletion"));
		
        return addressDao.deleteAddress(address);
	}
	
	@Override
	@Transactional
	public Boolean deleteAddressByUserId(Integer userId) {
		
		try {
			UserResponse user = userClient.getUserByUserId(userId);
		} catch (FeignException.NotFound e) {
			throw new UserNotFoundException("User Not Found");
		}
		
        return addressDao.deleteAddressByUserId(userId);
	}

	
}
