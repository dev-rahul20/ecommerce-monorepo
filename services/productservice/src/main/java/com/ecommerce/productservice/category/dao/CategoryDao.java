package com.ecommerce.productservice.category.dao;

import java.util.List;

import com.ecommerce.productservice.dto.CategoryResponseDto;
import com.ecommerce.productservice.entity.Category;

public interface CategoryDao {

	Category getCategoryById(Integer categoryId);

	List<CategoryResponseDto> getAllCategory();

	Boolean deleteCategory(Category category);

	Integer saveOrUpdateCategory(Category category);

}
