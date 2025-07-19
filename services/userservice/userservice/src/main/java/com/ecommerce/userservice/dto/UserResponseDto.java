package com.ecommerce.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

	private Integer userId;
	private String userFirstName;
	private String userLastName;
	private String userMiddleName;
	private Integer userAge;
	private Integer titleId;
	private Integer genderId;
}
