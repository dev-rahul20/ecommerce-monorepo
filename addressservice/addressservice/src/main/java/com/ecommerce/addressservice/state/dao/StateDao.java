package com.ecommerce.addressservice.state.dao;

import java.util.List;

import com.ecommerce.addressservice.entity.State;



public interface StateDao {

	List<State> getStateList();

	State getStateById(Integer stateId);

	Integer saveState(State state);

	Integer updateState(State state);

	boolean deleteState(Integer stateId);

}