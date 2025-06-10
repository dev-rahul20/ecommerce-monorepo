package com.ecommerce.productservice.product.dao;

import java.util.List;

import com.ecommerce.productservice.dto.ProductResponseDto;
import com.ecommerce.productservice.entity.Product;

public interface ProductDao {

	List<ProductResponseDto> getAllProducts();

	Product getProductByProductId(Integer productId);

	Integer saveProduct(Product product);

	Integer updateProduct(Product product);

	Boolean deleteProductByProductId(Product product);

}
