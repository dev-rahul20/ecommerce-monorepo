package com.ecommerce.addressservice.state.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.addressservice.entity.State;
import com.ecommerce.addressservice.exception.StateNotFoundException;
import com.ecommerce.addressservice.state.dao.StateDao;



@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StateDao dao;

    @Override
    @Transactional(readOnly = true)
    public List<State> getStateList() {
        return dao.getStateList();
    }

    @Override
    @Transactional(readOnly = true)
    public State getStateById(Integer stateId) {
        State State = dao.getStateById(stateId);
        if (State == null) {
            throw new StateNotFoundException("State with ID " + stateId + " not found");
        }
        return State;
    }

    @Override
    @Transactional
    public Integer saveState(State state) {
        return dao.saveState(state);
    }

    @Override
    @Transactional
    public Integer updateState(State state) {
        // Check if the State exists before updating
        State existing = dao.getStateById(state.getStateId());
        if (existing == null) {
            throw new StateNotFoundException("State with ID " + state.getStateId() + " not found for update");
        }
        return dao.updateState(state);
    }

    @Override
    @Transactional
    public boolean deleteState(Integer stateId) {
    	// Check if the State exists before deletion
    	State existing = dao.getStateById(stateId);
        if (existing == null) {
            throw new StateNotFoundException("State with ID " + stateId + " not found for deletion");
        }
        return dao.deleteState(stateId);
    }
}
