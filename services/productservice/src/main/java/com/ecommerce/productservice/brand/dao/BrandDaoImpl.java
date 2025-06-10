package com.ecommerce.productservice.brand.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.productservice.dto.BrandResponseDto;
import com.ecommerce.productservice.entity.Brand;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BrandDaoImpl implements BrandDao {

	private final SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<BrandResponseDto> getAllBrands() {
		return getSession().createQuery("FROM Brand", BrandResponseDto.class).getResultList();	
	}

	@Override
	public Brand getBrandByBrandId(Integer brandId) {
		return getSession().get(Brand.class, brandId);	
	}

	@Override
	public Integer saveBrand(Brand brand) {
		getSession().persist(brand);
		return brand.getId();
	}

	@Override
	public Integer updateBrand(Brand brand) {
		getSession().merge(brand);
		return brand.getId();
	}

	@Override
	public Boolean deleteBrandByBrandId(Brand brand) {
		getSession().remove(brand);
		return true;
	}

}
