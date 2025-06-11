package com.ecommerce.productservice.product.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.productservice.dto.CreateProductCompositeDto;
import com.ecommerce.productservice.dto.ProductImageRequestDto;
import com.ecommerce.productservice.dto.ProductRequestDto;
import com.ecommerce.productservice.dto.ProductResponseDto;
import com.ecommerce.productservice.dto.ProductSpecificationRequestDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.entity.ProductImage;
import com.ecommerce.productservice.entity.ProductSpecification;
import com.ecommerce.productservice.exception.ProductImageNotSaveException;
import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.exception.ProductNotSaveException;
import com.ecommerce.productservice.exception.ProductNotUpdateException;
import com.ecommerce.productservice.exception.ProductSpecificationNotSaveException;
import com.ecommerce.productservice.product.dao.ProductDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductDao dao;
	private final ModelMapper modelMapper;
	private final S3Service s3Service;
	
	private Product checkProductExistOrNot(Integer productId) { 
		
		return Optional.ofNullable(dao.getProductByProductId(productId))
	                   .orElseThrow(() -> new ProductNotFoundException("Product with id: " + productId + " not found"));
	}
	
	private Integer saveProductImage(ProductImageRequestDto imageDto, Product product) {
        
		// Upload the image to AWS S3 and get the public URL
        String imageUrl = s3Service.uploadBase64Image(imageDto.getImageAaBase64String(), imageDto.getFileName());

        // Create and save the ProductImage entity
        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setImageUrl(imageUrl);
        productImage.setSortOrder(imageDto.getSortOrder());
        
        Integer savedProductImageId = dao.saveProductImage(productImage);
        
        return Optional.ofNullable(savedProductImageId)
        			   .filter(id -> id > 0)
        			   .orElseThrow(() -> new ProductImageNotSaveException("ProductImage not saved"));
    }

	private Integer saveProductSpecification(ProductSpecificationRequestDto productSpecDto, Product product) {
		
		ProductSpecification specification = modelMapper.map(productSpecDto, ProductSpecification.class);
		specification.setProduct(product);
		
		Integer savedProductSpecificationId = dao.saveProductSpecification(specification);
		
		return Optional.ofNullable(savedProductSpecificationId)
 			   		   .filter(id -> id > 0)
 			   		   .orElseThrow(() -> new ProductSpecificationNotSaveException("ProductSpecification not saved"));
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
	public Integer saveProduct(CreateProductCompositeDto dto) {
		
		ProductRequestDto 			   productDto 		   = dto.getProduct();
		List<ProductImageRequestDto>   productImageDtoList = dto.getImages();
		ProductSpecificationRequestDto productSpecDto      = dto.getSpecifications();
		
		Product product = modelMapper.map(productDto, Product.class);
		
		Integer savedProductId = Optional.ofNullable(dao.saveProduct(product))
			    						 .filter(id -> id > 0)
			    						 .orElseThrow(() -> new ProductNotSaveException("Product not saved"));
		
		// Upload images on AWS cloud using s3-service & save its metadata into DB.
		productImageDtoList.forEach(productImageDto -> saveProductImage(productImageDto, product) );
		
		//Save ProductSpecification Data into DB.
		saveProductSpecification(productSpecDto, product);
		
		return savedProductId;
	}

	@Override
	@Transactional
	public Integer updateProduct(Integer productId, ProductRequestDto dto) {
	    
		checkProductExistOrNot(productId); // Check product exists before updating

	    Product updatedProduct = modelMapper.map(dto, Product.class);
	    updatedProduct.setId(productId); 

	    Integer updatedProductId = dao.updateProduct(updatedProduct);

	    return Optional.ofNullable(updatedProductId)
	                   .filter(id -> id > 0)
	                   .orElseThrow(() -> new ProductNotUpdateException("Product not updated"));
	}

	@Override
	public Boolean deleteProductByProductId(Integer productId) {

		  Product product =	checkProductExistOrNot(productId); // Check product exists before deletion
		
		  return dao.deleteProductByProductId(product);	  
	}
	

	

	

}
