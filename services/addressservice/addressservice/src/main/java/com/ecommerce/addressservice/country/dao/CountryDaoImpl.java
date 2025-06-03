package com.ecommerce.addressservice.country.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecommerce.addressservice.entity.Country;



@Repository
public class CountryDaoImpl implements CountryDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Country> getCountryList() {
        return getSession().createQuery("FROM Country", Country.class).getResultList();
    }

    @Override
    public Country getCountryById(Integer countryId) {
        return getSession().createQuery("FROM Country WHERE countryId = :countryId", Country.class)
                           .setParameter("countryId", countryId)
                           .uniqueResult();
    }

    @Override
    public Integer saveCountry(Country country) {
        getSession().persist(country);
        return country.getCountryId();
    }

    @Override
    public Integer updateCountry(Country country) {
        getSession().merge(country);
        return country.getCountryId();
    }

    @Override
    public boolean deleteCountry(Integer countryId) {
        Country country = getSession().get(Country.class, countryId);
        if (country != null) {
            getSession().remove(country);
            return true;
        }
        return false;
    }
}
