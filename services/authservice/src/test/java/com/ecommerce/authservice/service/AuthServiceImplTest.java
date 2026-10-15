package com.ecommerce.authservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.ecommerce.authservice.dao.AuthDao;
import com.ecommerce.authservice.dto.TokenResponse;
import com.ecommerce.authservice.entity.AuthUser;
import com.ecommerce.authservice.entity.RefreshToken;
import com.ecommerce.authservice.exception.InvalidCredentialsException;
import com.ecommerce.security.jwt.AuthenticatedUser;
import com.ecommerce.security.jwt.JwtTokenService;

class AuthServiceImplTest {

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
	JwtTokenService tokens = new JwtTokenService("test-secret-that-is-at-least-32-bytes-long!",
			"ecommerce-auth-service", Duration.ofMinutes(15), Duration.ofMinutes(5), Duration.ofSeconds(30),
			Clock.systemUTC());
	AuthDao dao = mock(AuthDao.class);
	AuthServiceImpl service = new AuthServiceImpl(dao, encoder, tokens);
	AuthUser user;

	@BeforeEach
	void setUp() {
		ReflectionTestUtils.setField(service, "refreshTokenTtl", Duration.ofDays(7));
		user = AuthUser.builder().authUserId(3L).userServiceId(7L).username("rahul")
				.passwordHash(encoder.encode("s3cret!")).build();
		when(dao.findByUsername("rahul")).thenReturn(Optional.of(user));
		when(dao.findRoleNames(3L)).thenReturn(List.of("USER"));
	}

	@Test
	void loginIssuesValidAccessTokenAndStoresHashedRefreshToken() {
		TokenResponse response = service.login("rahul", "s3cret!", "1.2.3.4", "junit");

		AuthenticatedUser parsed = tokens.parse(response.accessToken());
		assertThat(parsed.username()).isEqualTo("rahul");
		assertThat(parsed.userId()).isEqualTo(7L);
		assertThat(parsed.roles()).containsExactly("USER");

		ArgumentCaptor<RefreshToken> saved = ArgumentCaptor.forClass(RefreshToken.class);
		verify(dao).saveRefreshToken(saved.capture());
		assertThat(saved.getValue().getTokenHash()).isEqualTo(AuthServiceImpl.hash(response.refreshToken()))
				.isNotEqualTo(response.refreshToken());
	}

	@Test
	void wrongPasswordFailsAndCountsAttempt() {
		assertThatThrownBy(() -> service.login("rahul", "wrong", null, null))
				.isInstanceOf(InvalidCredentialsException.class);
		verify(dao).updateFailedAttempts(3L, 1, false);
	}

	@Test
	void unknownUserFailsWithSameMessage() {
		when(dao.findByUsername("ghost")).thenReturn(Optional.empty());
		assertThatThrownBy(() -> service.login("ghost", "x", null, null))
				.isInstanceOf(InvalidCredentialsException.class).hasMessage("Invalid username or password");
	}

	@Test
	void lockedUserCannotLogIn() {
		user.setLocked(true);
		assertThatThrownBy(() -> service.login("rahul", "s3cret!", null, null))
				.isInstanceOf(InvalidCredentialsException.class);
	}

	@Test
	void refreshRotatesToken() {
		RefreshToken stored = RefreshToken.builder().authUser(user).tokenHash(AuthServiceImpl.hash("old"))
				.expiresAt(LocalDateTime.now().plusDays(1)).build();
		when(dao.findRefreshTokenByHash(AuthServiceImpl.hash("old"))).thenReturn(Optional.of(stored));

		TokenResponse response = service.refresh("old", null, null);

		assertThat(stored.getRevoked()).isTrue();
		assertThat(response.refreshToken()).isNotEqualTo("old");
		assertThat(tokens.parse(response.accessToken()).username()).isEqualTo("rahul");
	}

	@Test
	void reusedRefreshTokenRevokesAllSessions() {
		RefreshToken stored = RefreshToken.builder().authUser(user).tokenHash(AuthServiceImpl.hash("old"))
				.revoked(true).expiresAt(LocalDateTime.now().plusDays(1)).build();
		when(dao.findRefreshTokenByHash(anyString())).thenReturn(Optional.of(stored));

		assertThatThrownBy(() -> service.refresh("old", null, null)).isInstanceOf(InvalidCredentialsException.class);
		verify(dao).revokeAllRefreshTokens(3L);
	}

	@Test
	void createHashesPlainPassword() {
		when(dao.createAuthUser(any())).thenAnswer(inv -> {
			((AuthUser) inv.getArgument(0)).setAuthUserId(9L);
			return 9L;
		});
		var dto = new com.ecommerce.authservice.dto.AuthUserRequestDto();
		ReflectionTestUtils.setField(dto, "username", "new");
		ReflectionTestUtils.setField(dto, "email", "n@x.io");
		ReflectionTestUtils.setField(dto, "passwordHash", "plain-pass");

		service.createAuthUser(dto);

		ArgumentCaptor<AuthUser> saved = ArgumentCaptor.forClass(AuthUser.class);
		verify(dao).createAuthUser(saved.capture());
		assertThat(saved.getValue().getPasswordHash()).startsWith("$2");
		assertThat(encoder.matches("plain-pass", saved.getValue().getPasswordHash())).isTrue();
		verify(dao).assignRole(any(), eq("USER"));
	}
}
