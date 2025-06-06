package com.ecommerce.userservice.user.dao;

import java.util.List;

import com.ecommerce.userservice.dto.UserResponseDto;
import com.ecommerce.userservice.entity.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface UserDao {

	Integer createUser(User user);

	List<UserResponseDto> getAllUsers();

	UserResponseDto getByUserId(@NotNull @Positive Integer userId);

	Integer updateUser(User user);

	Boolean deleteUser(@NotNull @Positive Integer userId);

}
