package com.ecommerce.ecommerce_gateway.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;

import com.ecommerce.security.jwt.JwtTokenService;
import com.ecommerce.security.jwt.PublicPathMatcher;

import reactor.core.publisher.Mono;

class JwtAuthenticationGatewayFilterTest {

	JwtTokenService tokens = new JwtTokenService("test-secret-that-is-at-least-32-bytes-long!", "ecommerce-auth-service",
			Duration.ofMinutes(15), Duration.ofMinutes(5), Duration.ofSeconds(30), Clock.systemUTC());

	JwtAuthenticationGatewayFilter filter = new JwtAuthenticationGatewayFilter(tokens,
			new PublicPathMatcher(List.of("POST /auth/api/login", "POST /user/api")));

	@Test
	void blocksRequestWithoutToken() {
		Result r = run(MockServerHttpRequest.get("/cart/api/1"));
		assertThat(r.forwarded).isFalse();
		assertThat(r.status).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	void blocksInvalidToken() {
		Result r = run(MockServerHttpRequest.get("/cart/api/1").header("Authorization", "Bearer abc.def.ghi"));
		assertThat(r.forwarded).isFalse();
		assertThat(r.status).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	void forwardsValidToken() {
		String token = tokens.issueAccessToken("rahul", 1L, 1L, List.of("USER"));
		Result r = run(MockServerHttpRequest.get("/cart/api/1").header("Authorization", "Bearer " + token));
		assertThat(r.forwarded).isTrue();
	}

	@Test
	void loginAndRegisterArePublic() {
		assertThat(run(MockServerHttpRequest.post("/auth/api/login")).forwarded).isTrue();
		assertThat(run(MockServerHttpRequest.post("/user/api")).forwarded).isTrue();
		assertThat(run(MockServerHttpRequest.get("/user/api")).forwarded).isFalse();
	}

	@Test
	void traversalFromPublicPathIsNotPublic() {
		assertThat(run(MockServerHttpRequest.post("/auth/api/login/../../cart/api")).forwarded).isFalse();
	}

	record Result(boolean forwarded, HttpStatus status) {
	}

	Result run(MockServerHttpRequest.BaseBuilder<?> request) {
		MockServerWebExchange exchange = MockServerWebExchange.from(request);
		AtomicBoolean forwarded = new AtomicBoolean();
		GatewayFilterChain chain = ex -> {
			forwarded.set(true);
			return Mono.empty();
		};
		filter.filter(exchange, chain).block();
		return new Result(forwarded.get(), (HttpStatus) exchange.getResponse().getStatusCode());
	}
}
