package com.ecommerce.productservice.brand.dao;

import java.util.List;

import com.ecommerce.productservice.dto.BrandResponseDto;
import com.ecommerce.productservice.entity.Brand;

public interface BrandDao {

	List<BrandResponseDto> getAllBrands();

	Brand getBrandByBrandId(Integer brandId);

	Integer saveBrand(Brand brand);

	Integer updateBrand(Brand brand);

	Boolean deleteBrandByBrandId(Brand brand);

}
