package com.ecommerce.productservice.category.dao;

import java.util.List;

import com.ecommerce.productservice.dto.CategoryResponseDto;
import com.ecommerce.productservice.entity.Category;

public interface CategoryDao {

	Category getCategoryById(Integer categoryId);

	List<CategoryResponseDto> getAllCategory();

	Integer saveCategory(Category category);

	Integer updateCategory(Category category);

	Boolean deleteCategory(Category category);

}
