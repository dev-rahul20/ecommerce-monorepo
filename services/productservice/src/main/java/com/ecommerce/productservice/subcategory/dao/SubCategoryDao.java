package com.ecommerce.productservice.subcategory.dao;

import java.util.List;

import com.ecommerce.productservice.dto.SubCategoryResponseDto;
import com.ecommerce.productservice.entity.SubCategory;

public interface SubCategoryDao {

	List<SubCategoryResponseDto> getAllSubCategory();

	SubCategory getSubCategoryById(Integer subCategoryId);

	Integer saveSubCategory(SubCategory subCategory);

	Integer updateSubCategory(SubCategory subCategory);

	Boolean deleteSubCategory(SubCategory subCategory);
	
}
