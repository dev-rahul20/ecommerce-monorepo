package com.ecommerce.addressservice.state.dao;

import java.util.List;

import com.ecommerce.addressservice.dto.StateResponceDto;
import com.ecommerce.addressservice.entity.State;



public interface StateDao {

	List<StateResponceDto> getStateList();

	StateResponceDto getStateById(Integer stateId);

	Integer saveState(State state);

	Integer updateState(State state);

	State getState(Integer countryId);

	boolean deleteState(State state);

}