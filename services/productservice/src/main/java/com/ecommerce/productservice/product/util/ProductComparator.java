package com.ecommerce.productservice.product.util;

import java.util.Objects;

import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.entity.ProductSpecification;

public class ProductComparator {

	private ProductComparator() {}
	
	public static boolean isProductModified(Product existing, Product incoming) {
	    
		return !Objects.equals(existing.getName(), incoming.getName()) ||
	           !Objects.equals(existing.getDescription(), incoming.getDescription()) ||
	           !Objects.equals(existing.getPrice(), incoming.getPrice()) ||
	           !Objects.equals(existing.getBrand().getId(), incoming.getBrand().getId()) ||
	           !Objects.equals(existing.getSubCategory().getId(), incoming.getSubCategory().getId()) ||
	           !Objects.equals(existing.getSupplier().getId(), incoming.getSupplier().getId());
	}
    
	public static boolean isProductSpecModified(ProductSpecification existing, ProductSpecification incoming) {
		
		return !Objects.equals(existing.getSpecKey(), incoming.getSpecKey()) || 
			   !Objects.equals(existing.getSpecValue(), incoming.getSpecValue()) ||
			   !Objects.equals(existing.getProduct().getId(), incoming.getProduct().getId());
	}
    
}