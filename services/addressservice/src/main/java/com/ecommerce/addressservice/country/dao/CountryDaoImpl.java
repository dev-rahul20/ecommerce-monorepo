package com.ecommerce.addressservice.country.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.addressservice.dto.CountryResponceDto;
import com.ecommerce.addressservice.entity.Country;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Repository
public class CountryDaoImpl implements CountryDao {

	
    private final SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<CountryResponceDto> getCountryList() {
        return getSession().createQuery("FROM Country", CountryResponceDto.class).getResultList();
    }

    @Override
    public CountryResponceDto getCountryById(Integer countryId) {
        return getSession().createQuery("FROM Country WHERE countryId = :countryId", CountryResponceDto.class)
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
    public boolean deleteCountry(Country country) {
      
    	getSession().remove(country);
        return true;
    }

	@Override
	public Country getCountry(Integer countryId) {
		return getSession().get(Country.class, countryId);
	}
}
