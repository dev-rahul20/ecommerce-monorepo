package com.ecommerce.authservice.dao;

import java.util.List;
import java.util.Optional;

import com.ecommerce.authservice.entity.AuthUser;
import com.ecommerce.authservice.entity.RefreshToken;

public interface AuthDao {

	Long createAuthUser(AuthUser authUser);

	Long updateAuthUser(AuthUser authUser);

	Optional<AuthUser> findByUsername(String username);

	Optional<AuthUser> findById(Long authUserId);

	List<String> findRoleNames(Long authUserId);

	void assignRole(AuthUser authUser, String roleName);

	void saveRefreshToken(RefreshToken token);

	Optional<RefreshToken> findRefreshTokenByHash(String tokenHash);

	void updateFailedAttempts(Long authUserId, int failedAttempts, boolean locked);

	int revokeAllRefreshTokens(Long authUserId);
}
