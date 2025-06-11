package com.ecommerce.productservice.product.service;

import java.util.List;

import com.ecommerce.productservice.dto.CreateProductCompositeDto;
import com.ecommerce.productservice.dto.ProductRequestDto;
import com.ecommerce.productservice.dto.ProductResponseDto;

public interface ProductService {

	List<ProductResponseDto> getAllProducts();

	ProductResponseDto getProductByProductId(Integer productId);
	
	Integer saveProduct(CreateProductCompositeDto dto);

	Integer updateProduct(Integer productId, ProductRequestDto dto);

	Boolean deleteProductByProductId(Integer productId);

	



}
