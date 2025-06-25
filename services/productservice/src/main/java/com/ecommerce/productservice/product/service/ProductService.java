package com.ecommerce.productservice.product.service;

import java.util.List;

import com.ecommerce.productservice.dto.CreateProductCompositeDto;
import com.ecommerce.productservice.dto.ProductResponseDto;

public interface ProductService {

	List<ProductResponseDto> getAllProducts();

	ProductResponseDto getProductByProductId(Integer productId);
	
	Integer saveProduct(CreateProductCompositeDto dto);

	Boolean deleteProductByProductId(Integer productId);

	Integer updateProduct(Integer productId, Integer productSpecId, CreateProductCompositeDto dto);

}
