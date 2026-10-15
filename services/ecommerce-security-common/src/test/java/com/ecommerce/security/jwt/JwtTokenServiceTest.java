package com.ecommerce.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.jupiter.api.Test;

class JwtTokenServiceTest {

	static final String SECRET = "test-secret-that-is-at-least-32-bytes-long!";
	static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");

	static JwtTokenService service(String secret, Instant now) {
		return new JwtTokenService(secret, "ecommerce-auth-service", Duration.ofMinutes(15), Duration.ofMinutes(5),
				Duration.ofSeconds(30), Clock.fixed(now, ZoneOffset.UTC));
	}

	@Test
	void accessTokenRoundTrips() {
		JwtTokenService tokens = service(SECRET, NOW);
		String token = tokens.issueAccessToken("rahul", 7L, 3L, List.of("USER", "ADMIN"));

		AuthenticatedUser user = tokens.parse(token);

		assertThat(user.username()).isEqualTo("rahul");
		assertThat(user.userId()).isEqualTo(7L);
		assertThat(user.authUserId()).isEqualTo(3L);
		assertThat(user.roles()).containsExactly("USER", "ADMIN");
		assertThat(user.isService()).isFalse();
	}

	@Test
	void serviceTokenCarriesServiceRole() {
		JwtTokenService tokens = service(SECRET, NOW);

		AuthenticatedUser user = tokens.parse(tokens.issueServiceToken("user-service"));

		assertThat(user.username()).isEqualTo("service:user-service");
		assertThat(user.roles()).containsExactly(JwtTokenService.ROLE_SERVICE);
		assertThat(user.isService()).isTrue();
	}

	@Test
	void expiredTokenIsRejected() {
		String token = service(SECRET, NOW).issueAccessToken("rahul", 1L, 1L, List.of("USER"));
		JwtTokenService later = service(SECRET, NOW.plus(Duration.ofMinutes(16)));

		assertThatThrownBy(() -> later.parse(token)).isInstanceOf(InvalidTokenException.class)
				.hasMessageContaining("expired");
	}

	@Test
	void tokenSignedWithAnotherKeyIsRejected() {
		String forged = service("another-secret-that-is-also-32-bytes-long", NOW)
				.issueAccessToken("admin", 1L, 1L, List.of("ADMIN"));

		assertThatThrownBy(() -> service(SECRET, NOW).parse(forged)).isInstanceOf(InvalidTokenException.class);
	}

	@Test
	void tamperedPayloadIsRejected() {
		JwtTokenService tokens = service(SECRET, NOW);
		String[] parts = tokens.issueAccessToken("rahul", 1L, 1L, List.of("USER")).split("\\.");
		String other = tokens.issueAccessToken("admin", 1L, 1L, List.of("ADMIN")).split("\\.")[1];

		assertThatThrownBy(() -> tokens.parse(parts[0] + "." + other + "." + parts[2]))
				.isInstanceOf(InvalidTokenException.class);
	}

	@Test
	void unsignedTokenIsRejected() {
		String header = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString("{\"alg\":\"none\"}".getBytes());
		String body = java.util.Base64.getUrlEncoder().withoutPadding()
				.encodeToString("{\"sub\":\"admin\",\"iss\":\"ecommerce-auth-service\",\"typ\":\"access\",\"exp\":9999999999}".getBytes());

		assertThatThrownBy(() -> service(SECRET, NOW).parse(header + "." + body + "."))
				.isInstanceOf(InvalidTokenException.class);
	}

	@Test
	void missingOrShortSecretFailsFast() {
		assertThatThrownBy(() -> service(null, NOW)).isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("JWT_SECRET");
		assertThatThrownBy(() -> service("too-short", NOW)).isInstanceOf(IllegalStateException.class);
	}
}
