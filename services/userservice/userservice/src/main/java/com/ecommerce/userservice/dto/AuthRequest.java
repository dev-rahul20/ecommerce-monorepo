package com.ecommerce.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthRequest {
	
	@NotBlank
    private String username;
	
	@NotBlank
    private String password;
}

