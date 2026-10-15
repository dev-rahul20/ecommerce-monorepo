package com.ecommerce.ecommerce_gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.ecommerce.security.jwt.InvalidTokenException;
import com.ecommerce.security.jwt.JwtTokenService;
import com.ecommerce.security.jwt.PublicPathMatcher;
import com.ecommerce.security.web.SecurityErrorBody;

import reactor.core.publisher.Mono;

/**
 * Rejects every routed request without a valid bearer token, except the paths in
 * ecommerce.security.public-paths (login, refresh, register). The token is passed on
 * unchanged, and each service validates it again, so calling a service directly
 * without a token is rejected too.
 */
@Component
public class JwtAuthenticationGatewayFilter implements GlobalFilter, Ordered {

	private static final String BEARER = "Bearer ";

	private final JwtTokenService tokenService;
	private final PublicPathMatcher publicPaths;

	public JwtAuthenticationGatewayFilter(JwtTokenService tokenService, PublicPathMatcher publicPaths) {
		this.tokenService = tokenService;
		this.publicPaths = publicPaths;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		HttpMethod method = request.getMethod();
		String path = request.getPath().pathWithinApplication().value();

		if (HttpMethod.OPTIONS.equals(method) || publicPaths.matches(method.name(), path)) {
			return chain.filter(exchange);
		}

		String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (header == null || !header.regionMatches(true, 0, BEARER, 0, BEARER.length())) {
			return reject(exchange.getResponse(), "Authentication required");
		}
		try {
			tokenService.parse(header.substring(BEARER.length()).trim());
		} catch (InvalidTokenException ex) {
			return reject(exchange.getResponse(), ex.getMessage());
		}
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		// Before routing and load balancing.
		return Ordered.HIGHEST_PRECEDENCE + 10;
	}

	private static Mono<Void> reject(ServerHttpResponse response, String message) {
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		response.getHeaders().set(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
		DataBuffer body = response.bufferFactory().wrap(SecurityErrorBody.json(HttpStatus.UNAUTHORIZED.name(), message));
		return response.writeWith(Mono.just(body));
	}
}
