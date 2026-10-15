package com.ecommerce.security.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Issues and validates HS256-signed JWTs. Every service holds the same secret
 * (env var {@code JWT_SECRET}), so any service can validate a token locally
 * without calling auth-service.
 */
public class JwtTokenService {

	public static final String TYPE_ACCESS = "access";
	public static final String TYPE_SERVICE = "service";
	public static final String ROLE_SERVICE = "SERVICE";

	static final String CLAIM_ROLES = "roles";
	static final String CLAIM_USER_ID = "uid";
	static final String CLAIM_AUTH_USER_ID = "aid";
	static final String CLAIM_TYPE = "typ";

	/** HS256 needs a key of at least 256 bits. */
	static final int MIN_SECRET_BYTES = 32;

	private static final Set<String> ACCEPTED_TYPES = Set.of(TYPE_ACCESS, TYPE_SERVICE);

	private final SecretKey key;
	private final String issuer;
	private final Duration accessTokenTtl;
	private final Duration serviceTokenTtl;
	private final Duration clockSkew;
	private final Clock clock;

	public JwtTokenService(String secret, String issuer, Duration accessTokenTtl, Duration serviceTokenTtl,
			Duration clockSkew, Clock clock) {

		if (secret == null || secret.isBlank()) {
			throw new IllegalStateException(
					"JWT secret is not configured. Set the JWT_SECRET environment variable (at least 32 characters).");
		}
		byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
		if (secretBytes.length < MIN_SECRET_BYTES) {
			throw new IllegalStateException(
					"JWT secret is too short: HS256 needs at least " + MIN_SECRET_BYTES + " bytes.");
		}
		this.key = Keys.hmacShaKeyFor(secretBytes);
		this.issuer = issuer;
		this.accessTokenTtl = accessTokenTtl;
		this.serviceTokenTtl = serviceTokenTtl;
		this.clockSkew = clockSkew;
		this.clock = clock;
	}

	public String issueAccessToken(String username, Long userId, Long authUserId, Collection<String> roles) {
		return issue(username, userId, authUserId, roles, TYPE_ACCESS, accessTokenTtl);
	}

	/** Token a service uses for its own calls when no end-user token is available (e.g. async work). */
	public String issueServiceToken(String serviceName) {
		return issue("service:" + serviceName, null, null, List.of(ROLE_SERVICE), TYPE_SERVICE, serviceTokenTtl);
	}

	public Duration getAccessTokenTtl() {
		return accessTokenTtl;
	}

	public Duration getServiceTokenTtl() {
		return serviceTokenTtl;
	}

	/**
	 * Verifies signature, issuer, expiry and token type.
	 *
	 * @throws InvalidTokenException if the token is not acceptable
	 */
	public AuthenticatedUser parse(String token) {
		if (token == null || token.isBlank()) {
			throw new InvalidTokenException("Missing token");
		}

		Claims claims;
		try {
			claims = Jwts.parser()
						 .verifyWith(key)
						 .requireIssuer(issuer)
						 .clock(() -> Date.from(clock.instant()))
						 .clockSkewSeconds(clockSkew.toSeconds())
						 .build()
						 .parseSignedClaims(token)
						 .getPayload();
		} catch (ExpiredJwtException ex) {
			throw new InvalidTokenException("Token has expired", ex);
		} catch (JwtException | IllegalArgumentException ex) {
			throw new InvalidTokenException("Invalid token", ex);
		}

		String type = claims.get(CLAIM_TYPE, String.class);
		if (!ACCEPTED_TYPES.contains(type)) {
			throw new InvalidTokenException("Unsupported token type");
		}
		if (claims.getExpiration() == null || claims.getSubject() == null) {
			throw new InvalidTokenException("Invalid token");
		}

		return new AuthenticatedUser(claims.getSubject(),
									 toLong(claims.get(CLAIM_USER_ID)),
									 toLong(claims.get(CLAIM_AUTH_USER_ID)),
									 toRoles(claims.get(CLAIM_ROLES)),
									 type);
	}

	private String issue(String subject, Long userId, Long authUserId, Collection<String> roles, String type,
			Duration ttl) {

		Instant now = clock.instant();
		var builder = Jwts.builder()
						  .id(UUID.randomUUID().toString())
						  .issuer(issuer)
						  .subject(subject)
						  .issuedAt(Date.from(now))
						  .expiration(Date.from(now.plus(ttl)))
						  .claim(CLAIM_TYPE, type)
						  .claim(CLAIM_ROLES, roles == null ? List.of() : List.copyOf(roles));
		if (userId != null) {
			builder.claim(CLAIM_USER_ID, userId);
		}
		if (authUserId != null) {
			builder.claim(CLAIM_AUTH_USER_ID, authUserId);
		}
		return builder.signWith(key, Jwts.SIG.HS256).compact();
	}

	private static Long toLong(Object value) {
		return value instanceof Number n ? n.longValue() : null;
	}

	private static List<String> toRoles(Object value) {
		if (value instanceof Collection<?> c) {
			return c.stream().map(String::valueOf).toList();
		}
		return List.of();
	}
}
