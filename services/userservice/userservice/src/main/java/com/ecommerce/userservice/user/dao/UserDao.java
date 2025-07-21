package com.ecommerce.userservice.user.dao;

import java.util.List;

import com.ecommerce.userservice.dto.UserResponseDto;
import com.ecommerce.userservice.entity.User;

public interface UserDao {

	Integer createUser(User user);

	List<UserResponseDto> getAllUsers();

	User getByUserId(Integer userId);

	Integer updateUser(User user);

	Boolean deleteUser(Integer userId);

}
