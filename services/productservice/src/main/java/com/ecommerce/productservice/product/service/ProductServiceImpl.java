package com.ecommerce.productservice.product.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.productservice.dto.ProductRequestDto;
import com.ecommerce.productservice.dto.ProductResponseDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.exception.ProductNotSaveException;
import com.ecommerce.productservice.exception.ProductNotUpdateException;
import com.ecommerce.productservice.product.dao.ProductDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductDao dao;
	private final ModelMapper modelMapper;
	
	private Product checkProductExistOrNot(Integer productId) { 
		
		return Optional.ofNullable(dao.getProductByProductId(productId))
	                   .orElseThrow(() -> new ProductNotFoundException("Product with id: " + productId + " not found"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ProductResponseDto> getAllProducts() {
		
		List<ProductResponseDto> list = dao.getAllProducts();
		
		return list == null ? Collections.emptyList() : list;
	}

	@Override
	@Transactional(readOnly = true)
	public ProductResponseDto getProductByProductId(Integer productId) {

		Product product =	checkProductExistOrNot(productId);

	    return modelMapper.map(product, ProductResponseDto.class);
	}

	@Override
	@Transactional
	public Integer saveProduct(ProductRequestDto dto) {
		
		Product product = modelMapper.map(dto, Product.class);
		
		Integer savedProductId = dao.saveProduct(product);
		
		return Optional.ofNullable(savedProductId)
					   .filter(id -> id < 0)
					   .orElseThrow(() -> new ProductNotSaveException("Product not saved"));
	}

	@Override
	@Transactional
	public Integer updateProduct(Integer productId, ProductRequestDto dto) {
	    
		checkProductExistOrNot(productId); // Check product exists before updating

	    Product updatedProduct = modelMapper.map(dto, Product.class);
	    updatedProduct.setId(productId); 

	    Integer updatedProductId = dao.updateProduct(updatedProduct);

	    return Optional.ofNullable(updatedProductId)
	                   .filter(id -> id < 0)
	                   .orElseThrow(() -> new ProductNotUpdateException("Product not updated"));
	}

	@Override
	public Boolean deleteProductByProductId(Integer productId) {

		  Product product =	checkProductExistOrNot(productId); // Check product exists before deletion
		
		  return dao.deleteProductByProductId(product);	  
	}
	

	

	

}
