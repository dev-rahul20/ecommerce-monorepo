package com.ecommerce.userservice.user.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.userservice.dto.UserResponceDto;
import com.ecommerce.userservice.entity.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Integer createUser(User user) {
		getSession().persist(user);
		return user.getUserId();
	}

	@Override
	public List<UserResponceDto> getAllUsers() {
		return getSession().createQuery("FROM User", UserResponceDto.class).getResultList();
	}

	@Override
	public UserResponceDto getByUserId(@NotNull @Positive Integer userId) {
		return getSession().createQuery("FROM User WHERE userId = :userId", UserResponceDto.class)
				.setParameter("userId", userId).uniqueResult();
	}

	@Override
	public Integer updateUser(User user) {
		getSession().merge(user);
		return user.getUserId();
	}

	@Override
	public Boolean deleteUser(@NotNull @Positive Integer userId) {
		String hql = "DELETE FROM User WHERE userId = :userId";

		int result = getSession().createMutationQuery(hql).setParameter("userId", userId).executeUpdate();
		return result > 0;
	}

}
