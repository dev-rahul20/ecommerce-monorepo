package com.ecommerce.productservice.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.productservice.dto.ProductImageResponseDto;
import com.ecommerce.productservice.dto.ProductResponseDto;
import com.ecommerce.productservice.dto.ProductSpecificationResponseDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.entity.ProductImage;
import com.ecommerce.productservice.entity.ProductSpecification;

@Configuration
public class BeanConfiguration {

    @Bean
    ModelMapper modelMapper() {
	    ModelMapper mapper = new ModelMapper();
	    mapper.getConfiguration()
	        .setFieldMatchingEnabled(true)
	        .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
	        .setMatchingStrategy(MatchingStrategies.LOOSE);

	    mapper.typeMap(Product.class, ProductResponseDto.class)
	    .addMappings(typeMap -> {
	    	typeMap.map(src -> src.getSubCategory().getId(), ProductResponseDto::setSubCategoryId);
	    	typeMap.map(src -> src.getBrand().getId(), ProductResponseDto::setBrandId);
	    	typeMap.map(src -> src.getSupplier().getId(), ProductResponseDto::setSupplierId);
//	    	typeMap.map(Product::get, ProductResponseDto::setProductImages);
//	    	typeMap.map(Product::getProductSpecifications, ProductResponseDto::setProductSpecifications);
	    });

	    
	    mapper.createTypeMap(ProductImage.class, ProductImageResponseDto.class);
	    mapper.createTypeMap(ProductSpecification.class, ProductSpecificationResponseDto.class);
	    
	    return mapper;
	}

	
}
