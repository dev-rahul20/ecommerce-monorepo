package com.ecommerce.addressservice.state.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

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
    public List<State> getStateList() {
        return getSession().createQuery("FROM State", State.class).getResultList();
    }

    @Override
    public State getStateById(Integer stateId) {
        return getSession().createQuery("FROM State WHERE stateId = :stateId", State.class)
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
    public boolean deleteState(Integer stateId) {
        State state = getSession().get(State.class, stateId);
        if (state != null) {
            getSession().remove(state);
            return true;
        }
        return false;
    }
}
