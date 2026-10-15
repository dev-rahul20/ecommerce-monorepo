package com.ecommerce.authservice.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.authservice.dao.AuthDao;
import com.ecommerce.authservice.dto.AuthUserRequestDto;
import com.ecommerce.authservice.dto.TokenResponse;
import com.ecommerce.authservice.entity.AuthUser;
import com.ecommerce.authservice.entity.RefreshToken;
import com.ecommerce.authservice.exception.InvalidCredentialsException;
import com.ecommerce.authservice.exception.UserAuthDetailsNotSaveException;
import com.ecommerce.authservice.exception.UserAuthDetailsNotUpdateException;
import com.ecommerce.security.jwt.JwtTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	static final String DEFAULT_ROLE = "USER";
	private static final SecureRandom RANDOM = new SecureRandom();

	private final AuthDao dao;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenService jwtTokenService;

	@Value("${auth.refresh-token-ttl:7d}")
	private Duration refreshTokenTtl;

	@Transactional
	@Override
	public Long createAuthUser(AuthUserRequestDto dto) {

		AuthUser authUser = AuthUser.builder()
									.username(dto.getUsername())
									.userServiceId(dto.getUserServiceId())
									.email(dto.getEmail())
									.passwordHash(encode(dto.getPasswordHash()))
									.build();

		Long id = dao.createAuthUser(authUser);
		if (id == null || id <= 0) {
			throw new UserAuthDetailsNotSaveException("User Auth Details Not Save!!");
		}
		dao.assignRole(authUser, DEFAULT_ROLE);
		return id;
	}

	@Transactional
	@Override
	public Long updateAuthUser(AuthUserRequestDto dto) {

		AuthUser existing = Optional.ofNullable(dto.getAuthUserId())
									.flatMap(dao::findById)
									.orElseThrow(() -> new UserAuthDetailsNotUpdateException("User Auth Details Not Update!!"));

		// Only identity and credentials can change here; enabled/locked/attempt counters are not client-settable.
		if (dto.getUsername() != null) existing.setUsername(dto.getUsername());
		if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
		if (dto.getUserServiceId() != null) existing.setUserServiceId(dto.getUserServiceId());
		if (dto.getPasswordHash() != null && !dto.getPasswordHash().isBlank()) {
			existing.setPasswordHash(encode(dto.getPasswordHash()));
			dao.revokeAllRefreshTokens(existing.getAuthUserId());
		}
		return dao.updateAuthUser(existing);
	}

	@Transactional(noRollbackFor = InvalidCredentialsException.class)
	@Override
	public TokenResponse login(String username, String password, String ipAddress, String userAgent) {

		AuthUser user = dao.findByUsername(username).orElse(null);
		if (user == null) {
			// Burn comparable time so unknown users are not distinguishable by latency.
			passwordEncoder.matches(password, passwordEncoder.encode("dummy"));
			throw new InvalidCredentialsException();
		}
		if (!passwordEncoder.matches(password, user.getPasswordHash())) {
			dao.updateFailedAttempts(user.getAuthUserId(), user.getFailedAttempts() + 1, user.getLocked());
			throw new InvalidCredentialsException();
		}
		if (!Boolean.TRUE.equals(user.getEnabled()) || Boolean.TRUE.equals(user.getLocked())) {
			throw new InvalidCredentialsException();
		}
		if (user.getFailedAttempts() > 0) {
			dao.updateFailedAttempts(user.getAuthUserId(), 0, false);
		}
		return issueTokens(user, ipAddress, userAgent);
	}

	/** Rotating refresh: the presented token is revoked; reuse of a revoked token revokes the whole family. */
	@Transactional(noRollbackFor = InvalidCredentialsException.class)
	@Override
	public TokenResponse refresh(String refreshToken, String ipAddress, String userAgent) {

		RefreshToken stored = dao.findRefreshTokenByHash(hash(refreshToken))
								 .orElseThrow(() -> new InvalidCredentialsException("Invalid refresh token"));
		AuthUser user = stored.getAuthUser();

		if (Boolean.TRUE.equals(stored.getRevoked())) {
			dao.revokeAllRefreshTokens(user.getAuthUserId());
			throw new InvalidCredentialsException("Invalid refresh token");
		}
		if (stored.getExpiresAt().isBefore(LocalDateTime.now()) || !Boolean.TRUE.equals(user.getEnabled())
				|| Boolean.TRUE.equals(user.getLocked())) {
			throw new InvalidCredentialsException("Invalid refresh token");
		}
		stored.setRevoked(true);
		return issueTokens(user, ipAddress, userAgent);
	}

	@Transactional
	@Override
	public void logout(String refreshToken) {
		dao.findRefreshTokenByHash(hash(refreshToken)).ifPresent(t -> t.setRevoked(true));
	}

	private TokenResponse issueTokens(AuthUser user, String ipAddress, String userAgent) {

		List<String> roles = dao.findRoleNames(user.getAuthUserId());
		if (roles.isEmpty()) {
			roles = List.of(DEFAULT_ROLE);
		}
		String accessToken = jwtTokenService.issueAccessToken(user.getUsername(), user.getUserServiceId(),
				user.getAuthUserId(), roles);

		byte[] raw = new byte[32];
		RANDOM.nextBytes(raw);
		String refreshToken = Base64.getUrlEncoder().withoutPadding().encodeToString(raw);

		dao.saveRefreshToken(RefreshToken.builder()
										 .authUser(user)
										 .tokenHash(hash(refreshToken))
										 .ipAddress(ipAddress)
										 .userAgent(userAgent)
										 .expiresAt(LocalDateTime.now().plus(refreshTokenTtl))
										 .build());

		return new TokenResponse(accessToken, refreshToken, "Bearer", jwtTokenService.getAccessTokenTtl().toSeconds(),
				user.getUsername(), roles);
	}

	/** Callers send the password in the passwordHash field; anything that is not already bcrypt gets hashed. */
	private String encode(String value) {
		if (value == null || value.isBlank()) {
			throw new UserAuthDetailsNotSaveException("Password is required");
		}
		return value.startsWith("$2") ? value : passwordEncoder.encode(value);
	}

	static String hash(String token) {
		try {
			return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(token.getBytes(StandardCharsets.UTF_8)));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}
}
