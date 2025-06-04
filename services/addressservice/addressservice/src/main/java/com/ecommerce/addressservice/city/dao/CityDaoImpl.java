package com.ecommerce.addressservice.city.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.addressservice.dto.CityResponceDto;
import com.ecommerce.addressservice.entity.City;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Repository
public class CityDaoImpl implements CityDao {


    private final SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<CityResponceDto> getCityList() {
        return getSession().createQuery("FROM City", CityResponceDto.class).getResultList();
    }

    @Override
    public CityResponceDto getCityById(Integer cityId) {
        return getSession().createQuery("FROM City WHERE cityId = :cityId", CityResponceDto.class)
                           .setParameter("cityId", cityId)
                           .uniqueResult();
    }

    @Override
    public City getCity(Integer cityId) {
    	return getSession().get(City.class, cityId);
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
