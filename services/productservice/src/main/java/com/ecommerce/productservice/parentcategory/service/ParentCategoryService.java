package com.ecommerce.productservice.parentcategory.service;

import java.util.List;

import com.ecommerce.productservice.dto.ParentCategoryRequestDto;
import com.ecommerce.productservice.dto.ParentCategoryResponseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface ParentCategoryService {

	List<ParentCategoryResponseDto> getAllParentCategory();

	ParentCategoryResponseDto getParentCategoryById(@Positive @Valid Integer parentCategoryId);

	Integer saveParentCategory(@Valid ParentCategoryRequestDto dto);
	
	Integer updateParentCategory(@Positive @NotNull Integer parentCategoryId, @Valid ParentCategoryRequestDto dto);

	Boolean deleteParentCategory(@Positive @Valid Integer parentCategoryId);

}
