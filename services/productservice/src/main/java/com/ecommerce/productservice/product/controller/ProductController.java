package com.ecommerce.productservice.product.controller;

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

import com.ecommerce.productservice.dto.CreateProductCompositeDto;
import com.ecommerce.productservice.dto.ProductRequestDto;
import com.ecommerce.productservice.dto.ProductResponseDto;
import com.ecommerce.productservice.product.service.ProductService;
import com.ecommerce.productservice.util.ProductResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

	private final ProductService service;
	
	@GetMapping("get-all-products")
	public ProductResponse getAllProducts() {
		List<ProductResponseDto> products = service.getAllProducts();
		return new ProductResponse(true, HttpStatus.OK, "Successfully fetched data", products);
	}
	
	@GetMapping("get-by-product-id/{productId}")
	public ProductResponse getProductByProductId(@PathVariable @Positive @Valid Integer productId) {
		ProductResponseDto product = service.getProductByProductId(productId); // should throw ProductNotFoundException if not found
		return new ProductResponse(true, HttpStatus.OK, "Successfully fetched data", product);
	}
	
	@PostMapping("save")
	public ProductResponse saveProduct(@RequestBody @Valid CreateProductCompositeDto dto) {
		Integer savedProductId = service.saveProduct(dto);
		return new ProductResponse(true, HttpStatus.OK, "Product saved Successfully", savedProductId);
	}
	
	@PutMapping("update/{productId}")
	public ProductResponse updateProduct(@PathVariable @Positive @NotNull Integer productId, @RequestBody @Valid ProductRequestDto dto) {
		Integer updatedProductId = service.updateProduct(productId, dto);
		return new ProductResponse(true, HttpStatus.OK, "Product updated Successfully", updatedProductId);
	}
	
	@DeleteMapping("delete-by-product-id/{productId}")
	public ProductResponse deleteProductByProductId(@PathVariable @Positive @Valid Integer productId) {
		service.deleteProductByProductId(productId); // should throw ProductNotFoundException if not found
		return new ProductResponse(true, HttpStatus.OK, "Product deleted Successfully", null);
	}
	
}
