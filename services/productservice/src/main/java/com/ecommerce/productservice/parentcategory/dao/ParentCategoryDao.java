package com.ecommerce.productservice.parentcategory.dao;

import java.util.List;

import com.ecommerce.productservice.dto.ParentCategoryResponseDto;
import com.ecommerce.productservice.entity.ParentCategory;

public interface ParentCategoryDao {

	List<ParentCategoryResponseDto> getAllParentCategory();

	ParentCategory getParentCategoryById(Integer parentCategoryId);

	Integer saveParentCategory(ParentCategory parentCategory);

	Integer updateParentCategory(ParentCategory parentCategory);

	Boolean deleteParentCategory(ParentCategory parentCategory);

}
