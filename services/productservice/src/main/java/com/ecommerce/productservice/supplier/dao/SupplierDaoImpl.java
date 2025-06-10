package com.ecommerce.productservice.supplier.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.productservice.dto.SupplierResponseDto;
import com.ecommerce.productservice.entity.Supplier;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class SupplierDaoImpl implements SupplierDao {
	
	private final SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<SupplierResponseDto> getAllSupplier() {
		return getSession().createQuery("FROM Supplier", SupplierResponseDto.class).getResultList();
	}

	@Override
	public Supplier getSupplierBySupplierId(Integer supplierId) {
		return getSession().get(Supplier.class, supplierId);	
	}

	@Override
	public Integer saveSupplier(Supplier supplier) {
		getSession().persist(supplier);
		return supplier.getId();
	}

	@Override
	public Integer updateSupplier(Supplier supplier) {
		getSession().merge(supplier);
		return supplier.getId();
	}

	@Override
	public Boolean deleteSupplierBySupplierId(Supplier supplier) {
		getSession().remove(supplier);
		return true;
	}

}
