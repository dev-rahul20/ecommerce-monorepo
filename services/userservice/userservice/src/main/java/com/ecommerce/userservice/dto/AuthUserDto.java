package com.ecommerce.userservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDto {
    private Long id;
    private String username;
    private boolean enabled;
    private List<String> roles;
}
