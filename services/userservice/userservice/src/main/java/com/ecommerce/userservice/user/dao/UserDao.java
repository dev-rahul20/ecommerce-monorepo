package com.ecommerce.userservice.user.dao;

import java.util.List;

import com.ecommerce.userservice.dto.UserResponseDto;
import com.ecommerce.userservice.entity.User;

public interface UserDao {

	Long createUser(User user);

	List<UserResponseDto> getAllUsers();

	User getByUserId(Long userId);

	Long updateUser(User user);

	Boolean deleteUser(Long userId);

}
