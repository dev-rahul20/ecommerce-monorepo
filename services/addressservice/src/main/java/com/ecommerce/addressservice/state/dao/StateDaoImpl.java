package com.ecommerce.addressservice.state.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.addressservice.dto.StateResponceDto;
import com.ecommerce.addressservice.entity.State;

import lombok.AllArgsConstructor;



@Repository
@AllArgsConstructor
public class StateDaoImpl implements StateDao {


    private final SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<StateResponceDto> getStateList() {
        return getSession().createQuery("FROM State", StateResponceDto.class).getResultList();
    }

    @Override
    public StateResponceDto getStateById(Integer stateId) {
        return getSession().createQuery("FROM State WHERE stateId = :stateId", StateResponceDto.class)
                           .setParameter("stateId",stateId)
                           .uniqueResult();
    }

    @Override
    public Integer saveState(State state) {
        getSession().persist(state);
        return state.getStateId();
    }

    @Override
    public Integer updateState(State state) {
        getSession().merge(state);
        return state.getStateId();
    }

    @Override
    public boolean deleteState(State state) {
         getSession().remove(state);
         return true;
    }
    
    @Override
    public State getState(Integer stateId) {
    	return getSession().get(State.class, stateId);
    }

}
