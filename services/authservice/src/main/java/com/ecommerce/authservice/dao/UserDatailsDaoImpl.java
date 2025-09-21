//package com.ecommerce.authservice.dao;
//
//import java.util.Optional;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.stereotype.Repository;
//
//import com.ecommerce.userservice.entity.Users;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Repository
//public class UserDatailsDaoImpl implements UserDetailsDao {
//	
//	private final SessionFactory sessionFactory;
//
//	private Session getSession() {
//		return sessionFactory.getCurrentSession();
//	}
//
//	@Override
//	public Optional<Users> getByUsername(String username) {
//
//		String hql = "FROM Users AS u WHERE u.username = :username";
//		
//		return getSession().createQuery(hql, Users.class)
//						   .setParameter("username", username)
//						   .uniqueResultOptional();
//	}
//
//}
