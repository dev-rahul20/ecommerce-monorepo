package com.ecommerce.userservice.user.service;

import java.util.List;

import com.ecommerce.userservice.dto.UserRequestDto;
import com.ecommerce.userservice.dto.UserResponceDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface UserService {

	Integer createUser(UserRequestDto user);

	List<UserResponceDto> getAllUsers();

	UserResponceDto getByUserId(@NotNull @Positive Integer userId);

	Integer updateUser(@Valid @Positive Integer userId, @Valid UserResponceDto user);

	void deleteUser(@NotNull @Positive Integer userId);


}
