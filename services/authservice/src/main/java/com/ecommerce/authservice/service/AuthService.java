package com.ecommerce.authservice.service;

import com.ecommerce.authservice.dto.AuthUserRequestDto;
import com.ecommerce.authservice.dto.TokenResponse;

public interface AuthService {

	Long createAuthUser(AuthUserRequestDto dto);

	Long updateAuthUser(AuthUserRequestDto dto);

	TokenResponse login(String username, String password, String ipAddress, String userAgent);

	TokenResponse refresh(String refreshToken, String ipAddress, String userAgent);

	void logout(String refreshToken);
}
