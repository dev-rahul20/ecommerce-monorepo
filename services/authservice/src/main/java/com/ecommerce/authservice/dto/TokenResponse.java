package com.ecommerce.authservice.dto;

import java.util.List;

public record TokenResponse(String accessToken, String refreshToken, String tokenType, long expiresInSeconds,
		String username, List<String> roles) {
}
