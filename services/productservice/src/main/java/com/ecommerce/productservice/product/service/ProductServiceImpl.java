package com.ecommerce.productservice.product.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.productservice.brand.dao.BrandDao;
import com.ecommerce.productservice.dto.CreateProductCompositeDto;
import com.ecommerce.productservice.dto.ProductImageRequestDto;
import com.ecommerce.productservice.dto.ProductRequestDto;
import com.ecommerce.productservice.dto.ProductResponseDto;
import com.ecommerce.productservice.dto.ProductSpecificationRequestDto;
import com.ecommerce.productservice.entity.Brand;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.entity.ProductImage;
import com.ecommerce.productservice.entity.ProductSpecification;
import com.ecommerce.productservice.entity.SubCategory;
import com.ecommerce.productservice.entity.Supplier;
import com.ecommerce.productservice.exception.ProductImageNotDeleteException;
import com.ecommerce.productservice.exception.ProductImageNotFoundException;
import com.ecommerce.productservice.exception.ProductImageNotSaveException;
import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.exception.ProductNotSaveException;
import com.ecommerce.productservice.exception.ProductNotUpdateException;
import com.ecommerce.productservice.exception.ProductSpecificationNotSaveException;
import com.ecommerce.productservice.exception.handler.ProductSpecificationNotUpdateException;
import com.ecommerce.productservice.product.dao.ProductDao;
import com.ecommerce.productservice.product.util.ProductComparator;
import com.ecommerce.productservice.subcategory.dao.SubCategoryDao;
import com.ecommerce.productservice.supplier.dao.SupplierDao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductDao productDao;
	private final SubCategoryDao subCategoryDao;
	private final BrandDao brandDao;
	private final SupplierDao supplierDao;
	private final ModelMapper modelMapper;
	private final S3Service s3Service;
	
	private Product checkProductExistOrNot(Integer productId) { 
		
		return Optional.ofNullable(productDao.getProductByProductId(productId))
	                   .orElseThrow(() -> new ProductNotFoundException("Product with id: " + productId + " not found"));
	}
	
	private Integer saveProductImage(ProductImageRequestDto imageDto, Product product) {
	    
	    // Construct S3 key (e.g. "products/{productId}/filename.jpg")
	    String s3Key = "products/" + product.getId() + "/" + imageDto.getFileName();

	    // Upload image to S3 using the generated key
	    String imageUrl = s3Service.uploadBase64Image(imageDto.getImageAaBase64String(), s3Key); 

	    // Create and save ProductImage entity
	    ProductImage productImage = new ProductImage();
	    productImage.setProduct(product);
	    productImage.setImageUrl(imageUrl);
	    productImage.setS3Key(s3Key);
	    productImage.setSortOrder(imageDto.getSortOrder());

	    Integer savedProductImageId = productDao.saveProductImage(productImage);

	    return Optional.ofNullable(savedProductImageId)
	    			   .filter(id -> id > 0)
	    			   .orElseThrow(() -> new ProductImageNotSaveException("ProductImage not saved"));
	}

	private void deleteProductImage(ProductImageRequestDto imageDto) {
		
		 String s3Key = Optional.ofNullable(productDao.getS3KeyByImageId(imageDto.getId()))
				    			.orElseThrow(() -> new ProductImageNotFoundException("Image not found with id: " + imageDto.getId())); 
		 
		// Delete from AWS S3
		 DeleteObjectResponse deleteResponce = s3Service.deleteImageFromAWS(s3Key); 
			
		 if (!deleteResponce.sdkHttpResponse().isSuccessful()) {
			    throw new ProductImageNotDeleteException("Product Image not deleted from AWS " + 
			    		  deleteResponce.sdkHttpResponse().statusText().orElse(""));
		 }
		 
		 // Delete image from DB
		 int rowAffected = productDao.deleteImageById(imageDto.getId());
		 
		 if(rowAffected == 0) {
				throw new ProductImageNotDeleteException("Product image with id : "+imageDto.getId()+ "not deleted");
		 }
	}
	
	private void saveProductSpecification(ProductSpecificationRequestDto productSpecDto, Product product) {
		
		ProductSpecification specification = modelMapper.map(productSpecDto, ProductSpecification.class);
		specification.setProduct(product);
		
		Integer savedProductSpecificationId = productDao.saveProductSpecification(specification);
		
		if(savedProductSpecificationId == null || savedProductSpecificationId <= 0) {
			throw new ProductSpecificationNotSaveException("ProductSpecification not saved");
		}
	}
	
	private void updateProductSpecification(ProductSpecificationRequestDto productSpecDto, Integer productSpecId, Product product) {
	
		ProductSpecification existingSpecification = productDao.checkProductSpecificationExistOrNot(productSpecId);
		
		ProductSpecification specification = modelMapper.map(productSpecDto, ProductSpecification.class);
		
		specification.setId(productSpecId);
		specification.setProduct(product);
		
		if(ProductComparator.isProductSpecModified(existingSpecification, specification)) {
			
			Integer updateProductSpecificationId = productDao.updateProductSpecification(specification);
			
			if(updateProductSpecificationId == null || updateProductSpecificationId <= 0) {
				throw new ProductSpecificationNotUpdateException("ProductSpecification not updated");
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ProductResponseDto> getAllProducts() {
		
		List<ProductResponseDto> list = productDao.getAllProducts();
		
		return list == null ? Collections.emptyList() : list;
	}

	@Override
	@Transactional(readOnly = true)
	public ProductResponseDto getProductByProductId(Integer productId) {

		Product product =	checkProductExistOrNot(productId);

	    return modelMapper.map(product, ProductResponseDto.class);
	}

	//This method is used to map nested entities to prevent detached entities exception while saving into DB.
	@PostConstruct
	private void configureModelMapper() {
	    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

	    Converter<Integer, SubCategory> subCategoryConverter =
	        ctx -> ctx.getSource() == null ? null : subCategoryDao.getSubCategoryById(ctx.getSource());

	    Converter<Integer, Brand> brandConverter =
	        ctx -> ctx.getSource() == null ? null : brandDao.getBrandByBrandId(ctx.getSource());

	    Converter<Integer, Supplier> supplierConverter =
	        ctx -> ctx.getSource() == null ? null : supplierDao.getSupplierBySupplierId(ctx.getSource());

	    modelMapper.typeMap(ProductRequestDto.class, Product.class).addMappings(mapper -> {
	        mapper.skip(Product::setId); // Prevents ambiguity
	        mapper.using(subCategoryConverter).map(ProductRequestDto::getSubCategoryId, Product::setSubCategory);
	        mapper.using(brandConverter).map(ProductRequestDto::getBrandId, Product::setBrand);
	        mapper.using(supplierConverter).map(ProductRequestDto::getSupplierId, Product::setSupplier);
	    });
	}
	
	@Override 
	@Transactional
	public Integer saveProduct(CreateProductCompositeDto dto) {
		
		ProductRequestDto 			   productDto 		   = dto.getProduct();
		List<ProductImageRequestDto>   productImageDtoList = dto.getImages();
		ProductSpecificationRequestDto productSpecDto      = dto.getSpecifications();
		
		Product product = modelMapper.map(productDto, Product.class);
		
		Integer savedProductId = Optional.ofNullable(productDao.saveProduct(product))
			    						 .filter(id -> id > 0)
			    						 .orElseThrow(() -> new ProductNotSaveException("Product not saved"));
		
		//Upload images on AWS cloud using s3-service & save its metadata into DB.
		productImageDtoList.forEach(productImageDto -> saveProductImage(productImageDto, product) );
		
		//Save ProductSpecification Data into DB.
		saveProductSpecification(productSpecDto, product);
		
		return savedProductId;
	}

	@Override
	@Transactional
	public Integer updateProduct(Integer productId, Integer productSpecId, CreateProductCompositeDto dto) {
	    
		Product existingProduct = checkProductExistOrNot(productId); // Check product exists before updating
		
		ProductRequestDto 			   productDto 		   = dto.getProduct();
		List<ProductImageRequestDto>   productImageDtoList = dto.getImages();
		ProductSpecificationRequestDto productSpecDto      = dto.getSpecifications();
		
	    Product product = modelMapper.map(productDto, Product.class);
	    product.setId(productId);

	    Integer updatedProductId = productId;
	    
	    if(ProductComparator.isProductModified(existingProduct, product)) {

	    	updatedProductId = Optional.ofNullable(productDao.updateProduct(product))
					   				   .filter(id -> id > 0)
					   				   .orElseThrow(() -> new ProductNotUpdateException("Product not updated"));
	    }
	   
	    // Delete existing images from AWS S3 & DB then ReUpload to AWS cloud using s3-service & save its metadata into DB.
	  	productImageDtoList.forEach(productImageDto -> {
	  		deleteProductImage(productImageDto);
	  		saveProductImage(productImageDto, product);	
	  	});
	  		
	  	// Update ProductSpecification Data into DB.
	  	updateProductSpecification(productSpecDto, productSpecId, product);
	    
	    return updatedProductId;
	}

	@Override
	@Transactional
	public Boolean deleteProductByProductId(Integer productId) {

		Product product = checkProductExistOrNot(productId); // Check product exists before deletion

		List<ProductImage> productImages = productDao.getAllProductImagesByProductId(productId);
		for (ProductImage image : productImages) {
			// Delete from AWS
			DeleteObjectResponse response = s3Service.deleteImageFromAWS(image.getS3Key());
			if (!response.sdkHttpResponse().isSuccessful()) {
				throw new ProductImageNotDeleteException("Failed to delete image from S3: " + image.getS3Key());
			}

			// Delete from DB
			int deleted = productDao.deleteImageById(image.getId());
			if (deleted == 0) {
				throw new ProductImageNotDeleteException("Failed to delete image record from DB: " + image.getId());
			}
		}
		
		// 2. Delete product specification
	    ProductSpecification spec = productDao.checkProductSpecificationExistOrNotByProductId(productId);
	    if (spec != null) {
	        productDao.deleteProductSpecification(spec);
	    }

	    // 3. Delete product from DB
	    return productDao.deleteProductByProductId(product);
	}

}
