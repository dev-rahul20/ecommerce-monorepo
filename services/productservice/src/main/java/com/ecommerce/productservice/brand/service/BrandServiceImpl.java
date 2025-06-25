package com.ecommerce.productservice.brand.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.productservice.brand.dao.BrandDao;
import com.ecommerce.productservice.dto.BrandRequestDto;
import com.ecommerce.productservice.dto.BrandResponseDto;
import com.ecommerce.productservice.entity.Brand;
import com.ecommerce.productservice.exception.BrandNotFoundException;
import com.ecommerce.productservice.exception.BrandNotSaveException;
import com.ecommerce.productservice.exception.BrandNotUpdateException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BrandServiceImpl implements BrandService {

	private final BrandDao dao;
	private final ModelMapper modelMapper;

	private Brand checkBrandExistOrNot(Integer brandId) {

		return Optional.ofNullable(dao.getBrandByBrandId(brandId))
					   .orElseThrow(() -> new BrandNotFoundException("Brand with id: " + brandId + " not found"));
	}

	@Override
	@Transactional
	public List<BrandResponseDto> getAllBrands() {

		List<BrandResponseDto> list = dao.getAllBrands();

		return list == null ? Collections.emptyList() : list;
	}

	@Override
	@Transactional
	public BrandResponseDto getBrandById(Integer brandId) {
		Brand brand = checkBrandExistOrNot(brandId);

		return modelMapper.map(brand, BrandResponseDto.class);
	}

	@Override
	@Transactional
	public Integer saveBrand(BrandRequestDto dto) {
		
		Brand brand = modelMapper.map(dto, Brand.class);
		
		Integer savedBrandId = dao.saveBrand(brand);
		
		return Optional.ofNullable(savedBrandId)
					   .filter(id -> id > 0)
					   .orElseThrow(() -> new BrandNotSaveException("Brand not saved"));
	}

	@Override
	@Transactional
	public Integer updateBrand(Integer brandId, BrandRequestDto dto) {
		
		checkBrandExistOrNot(brandId); 

	    Brand brand = modelMapper.map(dto, Brand.class);
	    brand.setId(brandId); 

	    Integer updatedBrandId = dao.updateBrand(brand);

	    return Optional.ofNullable(updatedBrandId)
	                   .filter(id -> id > 0)
	                   .orElseThrow(() -> new BrandNotUpdateException("Brand not updated"));
	}

	@Override
	@Transactional
	public Boolean deleteBrand(Integer brandId) {
		
		 Brand brand =	checkBrandExistOrNot(brandId);
			
		 return dao.deleteBrandByBrandId(brand);
	}

}
