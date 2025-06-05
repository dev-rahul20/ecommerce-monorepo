package com.ecommerce.userservice.user.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.userservice.dto.UserRequestDto;
import com.ecommerce.userservice.dto.UserResponseDto;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.exception.UserNotFoundException;
import com.ecommerce.userservice.exception.UserNotSaveException;
import com.ecommerce.userservice.user.dao.UserDao;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Transactional
	public Integer createUser(UserRequestDto dto) {

		User user = modelMapper.map(dto, User.class);
		Integer createUserId = dao.createUser(user);

		return Optional.ofNullable(createUserId).filter(id -> id > 0)
				.orElseThrow(() -> new UserNotSaveException("Error while saving address"));

	}

	@Override
	public List<UserResponseDto> getAllUsers() {
		return dao.getAllUsers();
	}

	@Override
	public UserResponseDto getByUserId(@NotNull @Positive Integer userId) {
		UserResponseDto user = dao.getByUserId(userId);
		Optional.ofNullable(user).orElseThrow(() -> new UserNotFoundException("User not found"));
		return user;
	}

	@Override
	public Integer updateUser(@Valid @Positive Integer userId, @Valid UserResponseDto dto) {

		User user = modelMapper.map(dto, User.class);
		user.setUserId(userId);
		Integer updatedUserId = dao.updateUser(user);

		return Optional.ofNullable(updatedUserId).filter(id -> id > 0)
				.orElseThrow(() -> new UserNotFoundException("Error while Updating User"));
	}

	@Override
	public void deleteUser(@NotNull @Positive Integer userId) {
		Optional.ofNullable(dao.getByUserId(userId))
				.orElseThrow(() -> new UserNotFoundException("User not found for deletion"));

		return dao.deleteUser(userId);
	}

}
