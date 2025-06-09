package com.ecommerce.productservice.product.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.productservice.dto.ProductResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProductDaoImpl implements ProductDao {
	
    private final SessionFactory sessionFactory;
	
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
	@Override
	public List<ProductResponseDto> getAllProducts() {
		return getSession().createQuery("FROM Products", ProductResponseDto.class).getResultList();											 
	}

	@Override
	public ProductResponseDto getProductByProductId(Integer productId) {
		return getSession().get(ProductResponseDto.class, productId);					 
	}

	
	
}
