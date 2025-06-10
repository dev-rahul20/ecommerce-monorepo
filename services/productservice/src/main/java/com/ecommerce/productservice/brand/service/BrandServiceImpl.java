package com.ecommerce.productservice.brand.service;

import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.brand.dao.BrandDao;
import com.ecommerce.productservice.dto.BrandRequestDto;
import com.ecommerce.productservice.dto.BrandResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BrandServiceImpl implements BrandService {
	
	private final BrandDao dao;
	private final ModelMapper modelMapper;
	
	@Override
	public List<BrandResponseDto> getAllBrands() {
		
		List<BrandResponseDto> list = dao.getAllBrands();
		
		return list == null ? Collections.emptyList() : list;
	}

	@Override
	public BrandResponseDto getBrandById(Integer brandId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer saveBrand(BrandRequestDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateBrand(Integer brandId, BrandRequestDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteBrand(Integer brandId) {
		// TODO Auto-generated method stub
		return null;
	}

}
