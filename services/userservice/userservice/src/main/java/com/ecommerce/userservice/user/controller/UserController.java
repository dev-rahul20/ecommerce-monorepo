package com.ecommerce.userservice.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.userservice.dto.UserRequestDto;
import com.ecommerce.userservice.dto.UserResponseDto;
import com.ecommerce.userservice.user.service.UserService;
import com.ecommerce.userservice.util.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping
	public UserResponse createUser(@RequestBody @Valid UserRequestDto user) {
		Integer craeteUserId = service.createUser(user);
		return new UserResponse(true, HttpStatus.CREATED, "User Created Successfully!!", craeteUserId);
	}

	@GetMapping
	public UserResponse getAllUsers() {
		List<UserResponseDto> list = service.getAllUsers();
		return new UserResponse(true, HttpStatus.OK, "Successfully fetched Users!!", list);
	}
	
	@GetMapping("/{userId}")
    public UserResponse getUserByUserId(@PathVariable @NotNull @Positive Integer userId) {
        UserResponseDto user = service.getByUserId(userId);
        return new UserResponse(true, HttpStatus.OK, "User found Successfully!!", user);
    }
	
	@PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable @Valid @Positive Integer userId, @RequestBody @Valid UserResponseDto user) {
        Integer updateUserId = service.updateUser(userId, user);
        return new UserResponse(true, HttpStatus.OK, "User Updated Successfully!!", updateUserId);
    }

    @DeleteMapping("/{userId}")
    public UserResponse deleteUser(@PathVariable @NotNull @Positive Integer userId) {
        service.deleteUser(userId); 
        return new UserResponse(true, HttpStatus.OK, "User Deleted Successfully!!", null);
    }

}
