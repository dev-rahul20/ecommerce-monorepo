package com.ecommerce.productservice.category.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.productservice.dto.CategoryResponseDto;
import com.ecommerce.productservice.entity.Category;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class CategoryDaoImpl implements CategoryDao{

	private final SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<CategoryResponseDto> getAllCategory() {
		return getSession().createQuery("FROM Category", CategoryResponseDto.class).getResultList();
	}
	
	@Override
	public Category getCategoryById(Integer categoryId) {
		return getSession().get(Category.class, categoryId);
	}

	@Override
	public Integer saveCategory(Category category) {
		getSession().persist(category);
		return category.getId();
	}

	@Override
	public Integer updateCategory(Category category) {
		getSession().merge(category);
		return category.getId();
	}

	@Override
	public Boolean deleteCategory(Category category) {
		getSession().remove(category);
		return true;
	}
	
}
