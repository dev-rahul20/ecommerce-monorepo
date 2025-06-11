package com.ecommerce.productservice.subcategory.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.productservice.dto.SubCategoryRequestDto;
import com.ecommerce.productservice.dto.SubCategoryResponseDto;
import com.ecommerce.productservice.entity.SubCategory;
import com.ecommerce.productservice.exception.SubCategoryNotFoundException;
import com.ecommerce.productservice.exception.SubCategoryNotSaveException;
import com.ecommerce.productservice.exception.SubCategoryNotUpdateException;
import com.ecommerce.productservice.subcategory.dao.SubCategoryDao;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SubCategoryServiceImpl implements SubCategoryService {

	
	private final SubCategoryDao dao;
	private final ModelMapper modelMapper;
	
	private SubCategory checkSubCategoryExistOrNot(Integer subCategoryId) {
		
		SubCategory subCategory = dao.getSubCategoryById(subCategoryId);
		
		return Optional.ofNullable(subCategory)
					   .orElseThrow(() -> new SubCategoryNotFoundException("SubCategory with id : "+subCategoryId+" not exist"));
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<SubCategoryResponseDto> getAllSubCategory() {
		
		 List<SubCategoryResponseDto> list = dao.getAllSubCategory();
		
		return list == null ? Collections.emptyList() : list;
	}
	
	@Override
	@Transactional(readOnly = true)
	public SubCategoryResponseDto getSubCategoryById(Integer subCategoryId) {
		
		SubCategory subCategory = checkSubCategoryExistOrNot(subCategoryId);
		
		return modelMapper.map(subCategory, SubCategoryResponseDto.class);
	}
	
	@Override
	@Transactional
	public Integer saveSubCategory(SubCategoryRequestDto dto) {
		
		SubCategory subCategory = modelMapper.map(dto, SubCategory.class);
	
		Integer savedSubCategoryId = dao.saveSubCategory(subCategory);
		
		return Optional.ofNullable(savedSubCategoryId)
					   .filter(id -> id > 0)
					   .orElseThrow(() -> new SubCategoryNotSaveException("SubCategory not saved"));
	}
	
	@Override
	@Transactional
	public Integer updateSubCategory(Integer subCategoryId, SubCategoryRequestDto dto) {
		
		checkSubCategoryExistOrNot(subCategoryId);
		
		SubCategory subCategory = modelMapper.map(dto, SubCategory.class);
		
		subCategory.setId(subCategoryId);
		
		Integer updatedSubCategoryId = dao.updateSubCategory(subCategory);
		
		return Optional.ofNullable(updatedSubCategoryId)
				   	   .filter(id -> id > 0)
				   	   .orElseThrow(() -> new SubCategoryNotUpdateException("SubCategory not updated"));
	}
	
	@Override
	@Transactional
	public Boolean deleteSubCategory(Integer subCategoryId) {
		
		SubCategory existingSubCategory = checkSubCategoryExistOrNot(subCategoryId);
		
		return dao.deleteSubCategory(existingSubCategory);
	}
	
	
	
	
}
