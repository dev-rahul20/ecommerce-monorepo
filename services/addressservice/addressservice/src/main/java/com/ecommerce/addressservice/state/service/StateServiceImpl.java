package com.ecommerce.addressservice.state.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.addressservice.dto.StateRequestDto;
import com.ecommerce.addressservice.dto.StateResponceDto;
import com.ecommerce.addressservice.entity.State;
import com.ecommerce.addressservice.exception.StateNotFoundException;
import com.ecommerce.addressservice.state.dao.StateDao;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class StateServiceImpl implements StateService {


    private final StateDao dao;
    private final ModelMapper modelMapper;
    
    
    @Override
    @Transactional(readOnly = true)
    public List<StateResponceDto> getStateList() {
        return dao.getStateList();
    }

    @Override
    @Transactional(readOnly = true)
    public StateResponceDto getStateById(Integer stateId) {
    	
    	StateResponceDto state = dao.getStateById(stateId);
    	
    	Optional.ofNullable(state).orElseThrow(() -> new StateNotFoundException("State with ID " + stateId + " not found"));
        
        return state;
    }

    @Override
    @Transactional
    public Integer saveState(StateRequestDto dto) {
    	
    	State state = modelMapper.map(dto, State.class);
    	
        return dao.saveState(state);
    }

    @Override
    @Transactional
    public Integer updateState(Integer stateid, StateRequestDto dto) {
        
    	State state = dao.getState(dto.getStateId()); // Check if the State exists before updating
        
        Optional.ofNullable(state).orElseThrow(() -> new StateNotFoundException("State with ID " + dto.getStateId() + " not found for update"));
       
        state.setStateId(stateid);
        
        return dao.updateState(state);
    }

    @Override
    @Transactional
    public boolean deleteState(Integer stateId) {
    	
    	State state = dao.getState(stateId); // Check if the State exists before deletion
        
    	Optional.ofNullable(state).orElseThrow(() -> new StateNotFoundException("State with ID " + stateId + " not found for deletion"));
    	
        return dao.deleteState(state);
    }
    
}
