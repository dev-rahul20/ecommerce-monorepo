package com.ecommerce.productservice.brand.controller;

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

import com.ecommerce.productservice.brand.service.BrandService;
import com.ecommerce.productservice.dto.BrandRequestDto;
import com.ecommerce.productservice.dto.BrandResponseDto;
import com.ecommerce.productservice.util.BrandResponce;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product-brand")
public class BrandController {
	
	private final BrandService service;
	
	@GetMapping("get-all")
	public BrandResponce getAllBrand() {
		List<BrandResponseDto> brands = service.getAllBrands();
		return new BrandResponce(true, HttpStatus.OK, "Successfully fetched data", brands);
	}
	
	@GetMapping("get-by-id/{brandId}")
	public BrandResponce getBrandById(@PathVariable @Positive @Valid Integer brandId) {
		BrandResponseDto brand = service.getBrandById(brandId); // should throw BrandNotFoundException if not found
		return new BrandResponce(true, HttpStatus.OK, "Successfully fetched data", brand);
	}
	
	@PostMapping("save")
	public BrandResponce saveBrand(@RequestBody @Valid BrandRequestDto dto) {
		Integer savedBrandId = service.saveBrand(dto);
		return new BrandResponce(true, HttpStatus.OK, "Brand saved Successfully", savedBrandId);
	}
	
	@PutMapping("update/{brandId}")
	public BrandResponce updateBrand(@PathVariable @Positive @NotNull Integer brandId, @RequestBody @Valid BrandRequestDto dto) {
		Integer updatedBrandId = service.updateBrand(brandId, dto);
		return new BrandResponce(true, HttpStatus.OK, "Brand updated Successfully", updatedBrandId);
	}
	
	@DeleteMapping("delete/{brandId}")
	public BrandResponce deleteBrand(@PathVariable @Positive @Valid Integer brandId) {
		Boolean isDeleted = service.deleteBrand(brandId); // should throw BrandNotFoundException if not found
		return new BrandResponce(true, HttpStatus.OK, "Brand deleted Successfully", isDeleted);
	}

}
