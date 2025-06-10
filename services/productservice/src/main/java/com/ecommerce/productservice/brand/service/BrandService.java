package com.ecommerce.productservice.brand.service;

import java.util.List;

import com.ecommerce.productservice.dto.BrandRequestDto;
import com.ecommerce.productservice.dto.BrandResponseDto;

public interface BrandService {

	List<BrandResponseDto> getAllBrands();

	BrandResponseDto getBrandById(Integer brandId);

	Integer saveBrand(BrandRequestDto dto);

	Integer updateBrand(Integer brandId, BrandRequestDto dto);

	Boolean deleteBrand(Integer brandId);

}
