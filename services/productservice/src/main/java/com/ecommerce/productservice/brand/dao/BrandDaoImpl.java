package com.ecommerce.productservice.brand.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecommerce.productservice.dto.BrandResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BrandDaoImpl implements BrandDao {
	
	@Override
	public List<BrandResponseDto> getAllBrands() {
		// TODO Auto-generated method stub
		return null;
	}

}
