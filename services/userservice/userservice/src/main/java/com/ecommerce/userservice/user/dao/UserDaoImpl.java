package com.ecommerce.userservice.user.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecommerce.userservice.dto.UserResponseDto;
import com.ecommerce.userservice.entity.User;

@Repository
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
	public List<UserResponseDto> getAllUsers() {
		return getSession().createQuery("FROM User", UserResponseDto.class).getResultList();
	}

	@Override
	public User getByUserId(Integer userId) {
		return getSession().get(User.class, userId);
	}

	@Override
	public Integer updateUser(User user) {
		getSession().merge(user);
		return user.getUserId();
	}

	@Override
	public Boolean deleteUser(Integer userId) {
		String hql = "DELETE FROM User WHERE userId = :userId";

		int result = getSession().createMutationQuery(hql).setParameter("userId", userId).executeUpdate();
		return result > 0;
	}

}
