package com.ecommerce.productservice.product.dao;

import java.util.List;

import com.ecommerce.productservice.dto.ProductResponseDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.entity.ProductImage;
import com.ecommerce.productservice.entity.ProductSpecification;

public interface ProductDao {

	List<ProductResponseDto> getAllProducts();

	Product getProductByProductId(Integer productId);

	Integer saveProduct(Product product);

	Integer updateProduct(Product product);

	Boolean deleteProductByProductId(Product product);

	Integer saveProductImage(ProductImage productImage);

	Integer saveProductSpecification(ProductSpecification specification);

	String getS3KeyByImageId(Integer id);

	Integer deleteImageById(Integer id);

	Integer updateProductSpecification(ProductSpecification specification);

	ProductSpecification checkProductSpecificationExistOrNot(Integer productSpecId);

}
