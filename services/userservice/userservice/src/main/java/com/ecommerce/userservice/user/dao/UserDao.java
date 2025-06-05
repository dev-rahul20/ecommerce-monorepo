package com.ecommerce.userservice.user.dao;

import java.util.List;

import com.ecommerce.userservice.dto.UserResponceDto;
import com.ecommerce.userservice.entity.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface UserDao {

	Integer createUser(User user);

	List<UserResponceDto> getAllUsers();

	UserResponceDto getByUserId(@NotNull @Positive Integer userId);

	Integer updateUser(User user);

	Boolean deleteUser(@NotNull @Positive Integer userId);

}
