package com.ecommerce.userservice.security.dao;

import java.util.Optional;

import com.ecommerce.userservice.entity.Users;

public interface UserDetailsDao {

	Optional<Users> getByUsername(String username);

}
