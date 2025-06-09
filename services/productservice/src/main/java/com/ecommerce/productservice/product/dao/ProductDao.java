package com.ecommerce.productservice.product.dao;

import java.util.List;

import com.ecommerce.productservice.dto.ProductResponseDto;

public interface ProductDao {

	List<ProductResponseDto> getAllProducts();

	ProductResponseDto getProductByProductId(Integer productId);

}
