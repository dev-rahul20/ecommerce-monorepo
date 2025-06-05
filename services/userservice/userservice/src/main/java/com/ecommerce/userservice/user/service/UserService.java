package com.ecommerce.userservice.user.service;

import java.util.List;

import com.ecommerce.userservice.dto.UserRequestDto;
import com.ecommerce.userservice.dto.UserResponseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface UserService {

	Integer createUser(UserRequestDto user);

	List<UserResponseDto> getAllUsers();

	UserResponseDto getByUserId(@NotNull @Positive Integer userId);

	Integer updateUser(@Valid @Positive Integer userId, @Valid UserResponseDto user);

	Boolean deleteUser(@NotNull @Positive Integer userId);


}
