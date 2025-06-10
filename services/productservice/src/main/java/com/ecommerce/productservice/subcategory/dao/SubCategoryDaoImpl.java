package com.ecommerce.productservice.subcategory.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.productservice.dto.SubCategoryResponseDto;
import com.ecommerce.productservice.entity.SubCategory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class SubCategoryDaoImpl implements SubCategoryDao {

	private final SessionFactory sessionFactory;
	
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

	@Override
	public List<SubCategoryResponseDto> getAllSubCategory() {
		return getSession().createQuery("FROM SubCategory", SubCategoryResponseDto.class).getResultList();
	}

	@Override
	public SubCategory getSubCategoryById(Integer subCategoryId) {
		return getSession().get(SubCategory.class, subCategoryId);
	}

	@Override
	public Integer saveSubCategory(SubCategory subCategory) {
		getSession().persist(subCategory);
		return subCategory.getId();
	}

	@Override
	public Integer updateSubCategory(SubCategory subCategory) {
		getSession().merge(subCategory);
		return subCategory.getId();
	}

	@Override
	public Boolean deleteSubCategory(SubCategory subCategory) {
		getSession().remove(subCategory);
		return true;
	}
	
}
