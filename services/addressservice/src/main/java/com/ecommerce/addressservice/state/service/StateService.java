package com.ecommerce.addressservice.state.service;

import java.util.List;

import com.ecommerce.addressservice.dto.StateRequestDto;
import com.ecommerce.addressservice.dto.StateResponceDto;



public interface StateService {

	List<StateResponceDto> getStateList();

	StateResponceDto getStateById(Integer stateId);

	Integer saveState(StateRequestDto dto);

	Integer updateState(Integer stateid, StateRequestDto dto);

	boolean deleteState(Integer stateId);

}
