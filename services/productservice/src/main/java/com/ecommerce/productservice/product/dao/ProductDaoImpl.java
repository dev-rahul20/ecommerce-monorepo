package com.ecommerce.productservice.product.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.productservice.dto.ProductResponseDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.entity.ProductImage;
import com.ecommerce.productservice.entity.ProductSpecification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProductDaoImpl implements ProductDao {
	
    private final SessionFactory sessionFactory;
	
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
	@Override
	public List<ProductResponseDto> getAllProducts() {
		return getSession().createQuery("FROM Products", ProductResponseDto.class).getResultList();											 
	}

	@Override
	public Product getProductByProductId(Integer productId) {
		return getSession().get(Product.class, productId);					 
	}

	@Override
	public Integer saveProduct(Product product) {
		getSession().persist(product);
		return product.getId();
	}

	@Override
	public Integer saveProductImage(ProductImage productImage) {
		getSession().persist(productImage);
		return productImage.getId();
	}
	
	@Override
	public Integer saveProductSpecification(ProductSpecification specification) {
		getSession().persist(specification);
		return specification.getId();
	}
	
	@Override
	public Integer updateProductSpecification(ProductSpecification specification) {	
		getSession().merge(specification);
		return specification.getId();
	}
	
	@Override
	public Integer updateProduct(Product product) {
		getSession().merge(product);
		return product.getId();
	}

	@Override
	public Boolean deleteProductByProductId(Product product) {
		getSession().remove(product);
		return true;
	}

	@Override
	public String getS3KeyByImageId(Integer imageId) {
		
		String hql = "Select img.s3Key FROM ProductImage AS img WHERE img.id =:imageId";
		
		return getSession().createQuery(hql, String.class).setParameter("imageId", imageId).uniqueResult();
	}

	@Override
	public Integer deleteImageById(Integer imageId) {
		
		String hql = "DELETE FROM ProductImage AS img WHERE img.id =:imageId";
		
		return getSession().createMutationQuery(hql).setParameter("imageId", imageId).executeUpdate();
	}

	@Override
	public ProductSpecification checkProductSpecificationExistOrNot(Integer productSpecId) {
		return getSession().get(ProductSpecification.class, productSpecId);
	}


	
}
