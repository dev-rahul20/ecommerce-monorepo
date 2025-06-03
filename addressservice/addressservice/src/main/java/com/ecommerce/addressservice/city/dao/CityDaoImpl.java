package com.ecommerce.addressservice.city.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecommerce.addressservice.entity.City;



@Repository
public class CityDaoImpl implements CityDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<City> getCityList() {
        return getSession().createQuery("FROM City", City.class).getResultList();
    }

    @Override
    public City getCityById(Integer cityId) {
        return getSession().createQuery("FROM City WHERE cityId = :cityId", City.class)
                           .setParameter("cityId", cityId)
                           .uniqueResult();
    }

    @Override
    public Integer saveCity(City city) {
        getSession().persist(city);
        return city.getCityId();
    }

    @Override
    public Integer updateCity(City city) {
        getSession().merge(city);
        return city.getCityId();
    }

    @Override
    public boolean deleteCity(Integer cityId) {
        City city = getSession().get(City.class, cityId);
        if (city != null) {
            getSession().remove(city);
            return true;
        }
        return false;
    }
}
