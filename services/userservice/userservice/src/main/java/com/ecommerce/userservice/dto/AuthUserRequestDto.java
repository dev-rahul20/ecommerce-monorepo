package com.ecommerce.userservice.dto;

import com.ecommerce.userservice.validation.OnCreateValidation;
import com.ecommerce.userservice.validation.OnUpdateValidation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUserRequestDto {

	@Null(groups = OnCreateValidation.class)
	@NotNull(groups = OnUpdateValidation.class)
	private Long authUserId;

	@NotBlank
	@Size(min = 3, message="UserName Should Be Minimum 3 Character Long")
	private String username;
	
	private Long userServiceId;

	@NotNull
	@Email(message="Enter Valid Email Address")
	private String email;

	private Boolean emailVerified;

	private String passwordHash;

	private Boolean enabled;

	private Boolean locked;

	private Integer failedAttempts;

	private Long profileVersion;
	
}
