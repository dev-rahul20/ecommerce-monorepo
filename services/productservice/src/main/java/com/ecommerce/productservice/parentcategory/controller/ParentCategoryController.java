package com.ecommerce.productservice.parentcategory.controller;

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

import com.ecommerce.productservice.dto.ParentCategoryRequestDto;
import com.ecommerce.productservice.dto.ParentCategoryResponseDto;
import com.ecommerce.productservice.parentcategory.service.ParentCategoryService;
import com.ecommerce.productservice.util.ParentCategoryResponce;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/parent-cateogry")
public class ParentCategoryController {

	private final ParentCategoryService service;
	
	@GetMapping("get-all")
	public ParentCategoryResponce getAllParentCategory() {
		List<ParentCategoryResponseDto> parentCategories = service.getAllParentCategory();
		return new ParentCategoryResponce(true, HttpStatus.OK, "Successfully fetched data", parentCategories);
	}
	
	@GetMapping("get-by-id/{parentCategoryId}")
	public ParentCategoryResponce getParentCategoryById(@PathVariable @Positive @Valid Integer parentCategoryId) {
		ParentCategoryResponseDto parentCategory = service.getParentCategoryById(parentCategoryId); // should throw ParentCategoryNotFoundException if not found
		return new ParentCategoryResponce(true, HttpStatus.OK, "Successfully fetched data", parentCategory);
	}
	
	@PostMapping("save")
	public ParentCategoryResponce saveParentCategory(@RequestBody @Valid ParentCategoryRequestDto dto) {
		Integer savedParentCategoryId = service.saveParentCategory(dto);
		return new ParentCategoryResponce(true, HttpStatus.OK, "ParentCategory saved Successfully", savedParentCategoryId);
	}
	
	@PutMapping("update/{parentCategoryId}")
	public ParentCategoryResponce updateParentCategory(@PathVariable @Positive @NotNull Integer parentCategoryId, @RequestBody @Valid ParentCategoryRequestDto dto) {
		Integer updatedParentCategoryId = service.updateParentCategory(parentCategoryId, dto);
		return new ParentCategoryResponce(true, HttpStatus.OK, "ParentCategory updated Successfully", updatedParentCategoryId);
	}
	
	@DeleteMapping("delete/{parentCategoryId}")
	public ParentCategoryResponce deleteParentCategoryById(@PathVariable @Positive @Valid Integer parentCategoryId) {
		Boolean isDeleted = service.deleteParentCategory(parentCategoryId); // should throw ParentCategoryNotFoundException if not found
		return new ParentCategoryResponce(true, HttpStatus.OK, "ParentCategory deleted Successfully", isDeleted);
	}
	
	
}
