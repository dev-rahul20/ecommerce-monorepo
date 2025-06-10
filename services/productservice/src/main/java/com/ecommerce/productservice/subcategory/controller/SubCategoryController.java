package com.ecommerce.productservice.subcategory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productservice.dto.SubCategoryRequestDto;
import com.ecommerce.productservice.dto.SubCategoryResponseDto;
import com.ecommerce.productservice.subcategory.service.SubCategoryService;
import com.ecommerce.productservice.util.SubCategoryResponce;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/sub-category")
public class SubCategoryController {

	private final SubCategoryService service;
	
	@GetMapping("get-all")
	public SubCategoryResponce getAllSubCategory() {
		List<SubCategoryResponseDto> subCategories = service.getAllSubCategory();
		return new SubCategoryResponce(true, HttpStatus.OK, "Successfully fetched data", subCategories);
	}
	
	@GetMapping("get-by-id/{subCategoryId}")
	public SubCategoryResponce getSubCategoryById(@PathVariable @Positive @Valid Integer subCategoryId) {
		SubCategoryResponseDto subCategory = service.getSubCategoryById(subCategoryId); // should throw SubCategoryNotFoundException if not found
		return new SubCategoryResponce(true, HttpStatus.OK, "Successfully fetched data", subCategory);
	}
	
	@PostMapping("save")
	public SubCategoryResponce saveSubCategory(@RequestBody @Valid SubCategoryRequestDto dto) {
		Integer savedParentCategoryId = service.saveSubCategory(dto);
		return new SubCategoryResponce(true, HttpStatus.OK, "SubCategory saved Successfully", savedParentCategoryId);
	}
	
	@PutMapping("update/{subCategoryId}")
	public SubCategoryResponce updateSubCategory(@PathVariable @Positive @NotNull Integer subCategoryId, @RequestBody @Valid SubCategoryRequestDto dto) {
		Integer updatedParentCategoryId = service.updateSubCategory(subCategoryId, dto);
		return new SubCategoryResponce(true, HttpStatus.OK, "SubCategory updated Successfully", updatedParentCategoryId);
	}
	
	@DeleteMapping("delete/{subCategoryId}")
	public SubCategoryResponce deleteSubCategory(@PathVariable @Positive @Valid Integer subCategoryId) {
		Boolean isDeleted = service.deleteSubCategory(subCategoryId); // should throw SubCategoryNotFoundException if not found
		return new SubCategoryResponce(true, HttpStatus.OK, "SubCategory deleted Successfully", isDeleted);
	}
	
}
