package com.ecommerce.productservice.subcategory.dao;

import java.util.List;

import com.ecommerce.productservice.dto.SubCategoryResponseDto;
import com.ecommerce.productservice.entity.SubCategory;

public interface SubCategoryDao {

	List<SubCategoryResponseDto> getAllSubCategory();

	SubCategory getSubCategoryById(Integer subCategoryId);

	Boolean deleteSubCategory(SubCategory subCategory);

	Integer saveOrUpdateSubCategory(SubCategory subCategory);
	
}
