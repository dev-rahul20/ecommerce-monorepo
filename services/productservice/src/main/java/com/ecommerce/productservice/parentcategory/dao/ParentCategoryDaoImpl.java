package com.ecommerce.productservice.parentcategory.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.productservice.dto.ParentCategoryResponseDto;
import com.ecommerce.productservice.entity.ParentCategory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ParentCategoryDaoImpl implements ParentCategoryDao {
	
	private final SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<ParentCategoryResponseDto> getAllParentCategory() {
		return getSession().createQuery("FROM ParentCategory", ParentCategoryResponseDto.class).getResultList();
	}

	@Override
	public ParentCategory getParentCategoryById(Integer parentCategoryId) {
		return getSession().get(ParentCategory.class, parentCategoryId);
	}

	@Override
	public Integer saveParentCategory(ParentCategory parentCategory) {
		getSession().persist(parentCategory);
		return parentCategory.getId();
	}

	@Override
	public Integer updateParentCategory(ParentCategory parentCategory) {
		getSession().merge(parentCategory);
		return parentCategory.getId();
	}
	
	@Override
	public Boolean deleteParentCategory(ParentCategory parentCategory) {
		getSession().remove(parentCategory);
		return true;
	}
	
}
