package com.ecommerce.authservice.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.authservice.entity.AuthUser;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthDaoImpl implements AuthDao {

	private final SessionFactory factory;
	
	private Session getSession() {
		return factory.getCurrentSession();
	}
	
	@Override
	public Long createAuthUser(AuthUser authUser) {
		getSession().persist(authUser);
		return authUser.getAuthUserId();
	}

	@Override
	public Long updateAuthUser(AuthUser authUser) {
		getSession().merge(authUser);
		return authUser.getAuthUserId();
	}
	
}
