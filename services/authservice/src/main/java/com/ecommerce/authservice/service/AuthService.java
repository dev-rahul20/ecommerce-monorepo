package com.ecommerce.authservice.service;

import com.ecommerce.authservice.dto.AuthUserRequestDto;

import jakarta.validation.Valid;

public interface AuthService {

	Long createAuthUser(AuthUserRequestDto dto);

	Long updateAuthUser(@Valid AuthUserRequestDto dto);

}
