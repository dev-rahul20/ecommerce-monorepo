package com.ecommerce.productservice.brand.dao;

import java.util.List;

import com.ecommerce.productservice.dto.BrandResponseDto;

public interface BrandDao {

	List<BrandResponseDto> getAllBrands();

}
