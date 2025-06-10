package com.ecommerce.productservice.category.service;

import java.util.List;

import com.ecommerce.productservice.dto.CategoryRequestDto;
import com.ecommerce.productservice.dto.CategoryResponseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

public interface CategoryService {

	List<CategoryResponseDto> getAllCategory();

	CategoryResponseDto getCategoryById(Integer categoryId);

	Integer saveCategory(CategoryRequestDto dto);
	
	Integer updateCategory(Integer categoryId, CategoryRequestDto dto);

	Boolean deleteCategory(@Positive @Valid Integer categoryId);

}
