package com.ecommerce.productservice.product.service;

import java.util.List;

import com.ecommerce.productservice.dto.ProductRequestDto;
import com.ecommerce.productservice.dto.ProductResponseDto;

public interface ProductService {

	List<ProductResponseDto> getAllProducts();

	ProductResponseDto getProductByProductId(Integer productId);
	
	Integer saveProduct(ProductRequestDto dto);

	Integer updateProduct(ProductRequestDto dto);

	void deleteProductByProductId(Integer productId);

	



}
