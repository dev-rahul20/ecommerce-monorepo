package com.ecommerce.userservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateUserCompositeDto {

	@NotNull
	@Valid
	private UserRequestDto userRequest;
	
	@NotNull
	@Valid
	private AuthUserRequestDto authUserRequest;
	
}
