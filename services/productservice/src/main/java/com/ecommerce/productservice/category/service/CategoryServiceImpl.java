package com.ecommerce.productservice.category.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.productservice.category.dao.CategoryDao;
import com.ecommerce.productservice.dto.CategoryRequestDto;
import com.ecommerce.productservice.dto.CategoryResponseDto;
import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.exception.CategoryNotFoundException;
import com.ecommerce.productservice.exception.CategoryNotSaveException;
import com.ecommerce.productservice.exception.CategoryNotUpdateException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryDao categoryDao;
	private final ModelMapper modelMapper;
	
	private Category checkCategoryExistOrNot(Integer categoryId) {
		
		Category category = categoryDao.getCategoryById(categoryId);
		
		return Optional.ofNullable(category)
					   .orElseThrow(() -> new CategoryNotFoundException("Category with id : "+categoryId+" not found"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CategoryResponseDto> getAllCategory(){
		
		List<CategoryResponseDto> list = categoryDao.getAllCategory();
		
		return list == null ? Collections.emptyList() : list;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CategoryResponseDto getCategoryById(Integer categoryId){
		
		Category category = checkCategoryExistOrNot(categoryId);
		
		return modelMapper.map(category, CategoryResponseDto.class);
	}
	
	@Override
	@Transactional
	public Integer saveCategory(CategoryRequestDto dto){
		
		Category category = modelMapper.map(dto, Category.class);
		
		Integer savedCategoryId = categoryDao.saveOrUpdateCategory(category);
		
		return Optional.ofNullable(savedCategoryId)
					   .filter(id -> id > 0)
					   .orElseThrow(() -> new CategoryNotSaveException("Category not saved"));
	}

	@Override
	@Transactional
	public Integer updateCategory(Integer categoryId, CategoryRequestDto dto) {
		
		checkCategoryExistOrNot(categoryId);
		
		Category category = modelMapper.map(dto, Category.class);
		category.setId(categoryId);
		
		Integer updatedCategoryId = categoryDao.saveOrUpdateCategory(category);
		
		return Optional.ofNullable(updatedCategoryId)
					   .filter(id -> id > 0)
					   .orElseThrow(() -> new CategoryNotUpdateException("Category not updated"));
	}
	
	@Override
	@Transactional
	public Boolean deleteCategory(Integer categoryId) {
		
		Category category = checkCategoryExistOrNot(categoryId);
		
		return categoryDao.deleteCategory(category);
		
	}
	
	
}
