package com.ecommerce.authservice.dao;

import com.ecommerce.authservice.entity.AuthUser;

public interface AuthDao {

	Long createAuthUser(AuthUser authUser);

	Long updateAuthUser(AuthUser authUser);

}
