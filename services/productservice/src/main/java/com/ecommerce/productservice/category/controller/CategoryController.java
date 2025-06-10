package com.ecommerce.productservice.category.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productservice.category.service.CategoryService;
import com.ecommerce.productservice.dto.CategoryRequestDto;
import com.ecommerce.productservice.dto.CategoryResponseDto;
import com.ecommerce.productservice.util.CategoryResponce;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

	private final CategoryService service;
	
	@GetMapping("get-all")
	public CategoryResponce getAllCategory() {
		List<CategoryResponseDto> categories = service.getAllCategory();
		return new CategoryResponce(true, HttpStatus.OK, "Successfully fetched data", categories);
	}
	
	@GetMapping("get-by-id/{categoryId}")
	public CategoryResponce getCategoryById(@PathVariable @Positive @Valid Integer categoryId) {
		CategoryResponseDto category = service.getCategoryById(categoryId); // should throw CategoryNotFoundException if not found
		return new CategoryResponce(true, HttpStatus.OK, "Successfully fetched data", category);
	}
	
	@PostMapping("save")
	public CategoryResponce saveCategory(@RequestBody @Valid CategoryRequestDto dto) {
		Integer savedCategoryId = service.saveCategory(dto);
		return new CategoryResponce(true, HttpStatus.OK, "Category saved Successfully", savedCategoryId);
	}
	
	@PutMapping("update/{categoryId}")
	public CategoryResponce updateCategory(@PathVariable @Positive @NotNull Integer categoryId, @RequestBody @Valid CategoryRequestDto dto) {
		Integer updatedCategoryId = service.updateCategory(categoryId, dto);
		return new CategoryResponce(true, HttpStatus.OK, "Category updated Successfully", updatedCategoryId);
	}
	
	@DeleteMapping("delete/{categoryId}")
	public CategoryResponce deleteSubCategory(@PathVariable @Positive @Valid Integer categoryId) {
		Boolean isDeleted = service.deleteCategory(categoryId); // should throw CategoryNotFoundException if not found
		return new CategoryResponce(true, HttpStatus.OK, "Category deleted Successfully", isDeleted);
	}
	
}
