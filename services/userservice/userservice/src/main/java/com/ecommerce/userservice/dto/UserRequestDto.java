package com.ecommerce.userservice.dto;


import com.ecommerce.userservice.validation.OnCreateValidation;
import com.ecommerce.userservice.validation.OnUpdateValidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

	@Null(groups = OnCreateValidation.class)
	@NotNull(groups = OnUpdateValidation.class)
    private Long userId;

    @NotBlank
    @Size(min = 3, message="User's First Name Should Be Minimum 3 Character Long")
    private String userFirstName;

    @NotBlank
    @Size(min = 3, message="User's Last Name Should Be Minimum 3 Character Long")
    private String userLastName;

    private String userMiddleName;
   
    @NotNull
    @Positive
    private Integer userAge;

    @NotNull
    @Positive
    private Integer titleId;

    @NotNull
    @Positive
    private Integer genderId;
}
