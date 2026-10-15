package com.ecommerce.security.jwt;

import java.util.List;

/**
 * Identity carried inside an access or service token.
 *
 * @param username   token subject (user name, or "service:&lt;name&gt;" for service tokens)
 * @param userId     id of the user in user-service, when known
 * @param authUserId id of the credential row in auth-service, when known
 * @param roles      role names without the "ROLE_" prefix, e.g. USER, ADMIN, SERVICE
 * @param tokenType  {@link JwtTokenService#TYPE_ACCESS} or {@link JwtTokenService#TYPE_SERVICE}
 */
public record AuthenticatedUser(String username, Long userId, Long authUserId, List<String> roles, String tokenType) {

	public AuthenticatedUser {
		roles = roles == null ? List.of() : List.copyOf(roles);
	}

	public boolean hasRole(String role) {
		return roles.contains(role);
	}

	public boolean isService() {
		return JwtTokenService.TYPE_SERVICE.equals(tokenType);
	}
}
