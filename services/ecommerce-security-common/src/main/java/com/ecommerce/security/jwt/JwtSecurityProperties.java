package com.ecommerce.security.jwt;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <pre>
 * ecommerce.security.jwt.secret        # defaults to the JWT_SECRET environment variable
 * ecommerce.security.jwt.issuer        # default ecommerce-auth-service
 * ecommerce.security.jwt.access-token-ttl
 * ecommerce.security.jwt.service-token-ttl
 * ecommerce.security.public-paths      # e.g. POST /user,/auth/login  (everything else needs a token)
 * </pre>
 */
@ConfigurationProperties(prefix = "ecommerce.security")
public class JwtSecurityProperties {

	/** Paths reachable without a token, as "[METHOD ]/ant/pattern". */
	private List<String> publicPaths = new ArrayList<>();

	private final Jwt jwt = new Jwt();

	public List<String> getPublicPaths() {
		return publicPaths;
	}

	public void setPublicPaths(List<String> publicPaths) {
		this.publicPaths = publicPaths;
	}

	public Jwt getJwt() {
		return jwt;
	}

	public static class Jwt {

		private String secret;
		private String issuer = "ecommerce-auth-service";
		private Duration accessTokenTtl = Duration.ofMinutes(15);
		private Duration serviceTokenTtl = Duration.ofMinutes(5);
		private Duration clockSkew = Duration.ofSeconds(30);

		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}

		public String getIssuer() {
			return issuer;
		}

		public void setIssuer(String issuer) {
			this.issuer = issuer;
		}

		public Duration getAccessTokenTtl() {
			return accessTokenTtl;
		}

		public void setAccessTokenTtl(Duration accessTokenTtl) {
			this.accessTokenTtl = accessTokenTtl;
		}

		public Duration getServiceTokenTtl() {
			return serviceTokenTtl;
		}

		public void setServiceTokenTtl(Duration serviceTokenTtl) {
			this.serviceTokenTtl = serviceTokenTtl;
		}

		public Duration getClockSkew() {
			return clockSkew;
		}

		public void setClockSkew(Duration clockSkew) {
			this.clockSkew = clockSkew;
		}
	}
}
