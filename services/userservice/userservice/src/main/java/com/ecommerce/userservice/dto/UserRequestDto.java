package com.ecommerce.userservice.dto;


import com.ecommerce.userservice.validation.OnCreateValidation;
import com.ecommerce.userservice.validation.OnUpdateValidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private Integer adrId;

    @NotNull
    @Positive
    private Integer titleId;

    @NotNull
    @Positive
    private Integer genderId;
}
