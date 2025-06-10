package com.ecommerce.productservice.parentcategory.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.productservice.dto.ParentCategoryRequestDto;
import com.ecommerce.productservice.dto.ParentCategoryResponseDto;
import com.ecommerce.productservice.entity.ParentCategory;
import com.ecommerce.productservice.exception.ParentCategoryNotFoundException;
import com.ecommerce.productservice.exception.ParentCategoryNotSaveException;
import com.ecommerce.productservice.exception.ParentCategoryNotUpdateException;
import com.ecommerce.productservice.parentcategory.dao.ParentCategoryDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ParentCategoryServiceImpl implements ParentCategoryService {
	
	private final ParentCategoryDao dao;
	private final ModelMapper modelMapper;
	
	private ParentCategory checkParentCategoryExistOrNot(Integer parentCategoryId) {

		return Optional.ofNullable(dao.getParentCategoryById(parentCategoryId))
					   .orElseThrow(() -> new ParentCategoryNotFoundException("ParentCategory with id : "+parentCategoryId+" not found"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ParentCategoryResponseDto> getAllParentCategory() {
		
		List<ParentCategoryResponseDto> list = dao.getAllParentCategory();
		
		return list == null ? Collections.emptyList() : list;
	}

	@Override
	@Transactional(readOnly = true)
	public ParentCategoryResponseDto getParentCategoryById(Integer parentCategoryId) {
		
		ParentCategory parentCategory = checkParentCategoryExistOrNot(parentCategoryId); 

		return modelMapper.map(parentCategory, ParentCategoryResponseDto.class);
	}

	@Override
	@Transactional
	public Integer saveParentCategory(ParentCategoryRequestDto dto) {
		
		ParentCategory parentCategory = modelMapper.map(dto, ParentCategory.class);
		
		Integer savedParentCategoryId = dao.saveParentCategory(parentCategory);

		return Optional.ofNullable(savedParentCategoryId)
					   .filter(id -> id < 0)
					   .orElseThrow(() -> new ParentCategoryNotSaveException("ParentCategory Not Saved"));
	}
	
	@Override
	@Transactional
	public Integer updateParentCategory(Integer parentCategoryId, ParentCategoryRequestDto dto) {
		
		checkParentCategoryExistOrNot(parentCategoryId); 
		
		ParentCategory parentCategory = modelMapper.map(dao, ParentCategory.class);
		parentCategory.setId(parentCategoryId);
		
		Integer updatedParentCategoryId = dao.updateParentCategory(parentCategory);
		
		return Optional.ofNullable(updatedParentCategoryId)
					   .filter(id -> id < 0)
					   .orElseThrow(() -> new ParentCategoryNotUpdateException("ParentCategory not updated"));
	}

	@Override
	@Transactional
	public Boolean deleteParentCategory(Integer parentCategoryId) {
		
		ParentCategory parentCategory = checkParentCategoryExistOrNot(parentCategoryId);
		
		return dao.deleteParentCategory(parentCategory);
	}
	
	

}
