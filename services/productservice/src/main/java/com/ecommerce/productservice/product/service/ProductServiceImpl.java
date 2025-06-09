package com.ecommerce.productservice.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.productservice.dto.ProductRequestDto;
import com.ecommerce.productservice.dto.ProductResponseDto;
import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.product.dao.ProductDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductDao dao;

	@Override
	@Transactional(readOnly = true)
	public List<ProductResponseDto> getAllProducts() {
		return dao.getAllProducts();
	}

	@Override
	@Transactional(readOnly = true)
	public ProductResponseDto getProductByProductId(Integer productId) {
		
		ProductResponseDto dto = getProductByProductId(productId);
		
		Optional.ofNullable(dto)
				.orElseThrow(() -> new ProductNotFoundException("Product with id : "+productId+" not found"));
		return dto;
	}

	@Override
	public Integer saveProduct(ProductRequestDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateProduct(ProductRequestDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProductByProductId(Integer productId) {
		// TODO Auto-generated method stub
		
	}
	
	
	

	

}
