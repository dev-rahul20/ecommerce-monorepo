package com.ecommerce.userservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    private Integer userId;

    @NotBlank
    private String userFirstName;

    @NotBlank
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
