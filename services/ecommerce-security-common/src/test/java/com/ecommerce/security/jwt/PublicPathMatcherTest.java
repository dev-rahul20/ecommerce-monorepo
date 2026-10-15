package com.ecommerce.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class PublicPathMatcherTest {

	PublicPathMatcher matcher = new PublicPathMatcher(List.of("/auth/login", " POST /user ", "GET /public/**"));

	@Test
	void matchesAnyMethodWhenNoneGiven() {
		assertThat(matcher.matches("POST", "/auth/login")).isTrue();
		assertThat(matcher.matches("GET", "/auth/login")).isTrue();
	}

	@Test
	void respectsMethod() {
		assertThat(matcher.matches("POST", "/user")).isTrue();
		assertThat(matcher.matches("GET", "/user")).isFalse();
		assertThat(matcher.matches("DELETE", "/user")).isFalse();
	}

	@Test
	void doesNotMatchOtherPaths() {
		assertThat(matcher.matches("POST", "/user/5")).isFalse();
		assertThat(matcher.matches("POST", "/auth/create")).isFalse();
		assertThat(matcher.matches("GET", "/public/a/b")).isTrue();
	}

	@Test
	void neverMatchesTraversalOrEncodedPaths() {
		assertThat(matcher.matches("GET", "/public/../admin")).isFalse();
		assertThat(matcher.matches("GET", "/public/%2e%2e/admin")).isFalse();
		assertThat(matcher.matches("GET", "/public/x;jsessionid=1")).isFalse();
	}
}
