package com.ecommerce.productservice.subcategory.service;

import java.util.List;

import com.ecommerce.productservice.dto.SubCategoryRequestDto;
import com.ecommerce.productservice.dto.SubCategoryResponseDto;

public interface SubCategoryService {

	List<SubCategoryResponseDto> getAllSubCategory();

	SubCategoryResponseDto getSubCategoryById(Integer subCategoryId);

	Integer saveSubCategory(SubCategoryRequestDto dto);

	Integer updateSubCategory(Integer subCategoryId, SubCategoryRequestDto dto);

	Boolean deleteSubCategory(Integer subCategoryId);

}
