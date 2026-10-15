package com.ecommerce.security;

import java.time.Clock;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.ecommerce.security.jwt.JwtSecurityProperties;
import com.ecommerce.security.jwt.JwtTokenService;
import com.ecommerce.security.jwt.PublicPathMatcher;

/** Token service and public-path matcher; loaded in every app, servlet or reactive. */
@AutoConfiguration
@EnableConfigurationProperties(JwtSecurityProperties.class)
public class JwtAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	JwtTokenService jwtTokenService(JwtSecurityProperties properties, Environment environment) {
		JwtSecurityProperties.Jwt jwt = properties.getJwt();
		String secret = jwt.getSecret();
		if (secret == null || secret.isBlank()) {
			secret = environment.getProperty("JWT_SECRET");
		}
		return new JwtTokenService(secret, jwt.getIssuer(), jwt.getAccessTokenTtl(), jwt.getServiceTokenTtl(),
				jwt.getClockSkew(), Clock.systemUTC());
	}

	@Bean
	@ConditionalOnMissingBean
	PublicPathMatcher publicPathMatcher(JwtSecurityProperties properties) {
		return new PublicPathMatcher(properties.getPublicPaths());
	}
}
