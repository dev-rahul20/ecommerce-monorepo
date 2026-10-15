package com.ecommerce.security.relay;

import java.time.Duration;
import java.time.Instant;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ecommerce.security.jwt.JwtTokenService;

/**
 * Chooses the Authorization header for a call to another service: the caller's own
 * bearer token when we are inside an HTTP request, otherwise a short-lived service
 * token (background threads, Kafka listeners, retries on other threads).
 */
public class OutboundTokenProvider {

	private static final Duration REFRESH_MARGIN = Duration.ofSeconds(60);

	private final JwtTokenService tokenService;
	private final String serviceName;

	private String cachedServiceToken;
	private Instant cachedServiceTokenExpiry = Instant.MIN;

	public OutboundTokenProvider(JwtTokenService tokenService, String serviceName) {
		this.tokenService = tokenService;
		this.serviceName = serviceName;
	}

	public String authorizationHeader() {
		String incoming = incomingAuthorizationHeader();
		return incoming != null ? incoming : "Bearer " + serviceToken();
	}

	private static String incomingAuthorizationHeader() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		if (attributes instanceof ServletRequestAttributes servlet) {
			String header = servlet.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
			if (header != null && header.regionMatches(true, 0, "Bearer ", 0, 7)) {
				return header;
			}
		}
		return null;
	}

	synchronized String serviceToken() {
		Instant now = Instant.now();
		if (cachedServiceToken == null || now.plus(REFRESH_MARGIN).isAfter(cachedServiceTokenExpiry)) {
			cachedServiceToken = tokenService.issueServiceToken(serviceName);
			cachedServiceTokenExpiry = now.plus(tokenService.getServiceTokenTtl());
		}
		return cachedServiceToken;
	}
}
