package com.ecommerce.userservice.user.service;

import java.util.List;

import com.ecommerce.userservice.dto.CreateUserCompositeDto;
import com.ecommerce.userservice.dto.UserResponseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface UserService {

	Long createUser(CreateUserCompositeDto user);

	List<UserResponseDto> getAllUsers();

	UserResponseDto getByUserId(@NotNull @Positive Long userId);

	Long updateUser(@Valid @Positive Long userId, @Valid UserResponseDto user);

	Boolean deleteUser(@NotNull @Positive Long userId);


}
