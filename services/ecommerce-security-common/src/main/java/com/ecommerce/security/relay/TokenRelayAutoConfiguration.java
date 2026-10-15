package com.ecommerce.security.relay;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;

import com.ecommerce.security.JwtAutoConfiguration;
import com.ecommerce.security.jwt.JwtTokenService;

import feign.RequestInterceptor;

/** Makes service-to-service calls (Feign and WebClient) carry a JWT automatically. */
@AutoConfiguration(after = JwtAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class TokenRelayAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	OutboundTokenProvider outboundTokenProvider(JwtTokenService tokenService, Environment environment) {
		return new OutboundTokenProvider(tokenService, environment.getProperty("spring.application.name", "unknown"));
	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(name = "feign.RequestInterceptor")
	static class FeignRelay {

		@Bean
		RequestInterceptor jwtRelayRequestInterceptor(OutboundTokenProvider tokens) {
			return template -> {
				if (!template.headers().containsKey(HttpHeaders.AUTHORIZATION)) {
					template.header(HttpHeaders.AUTHORIZATION, tokens.authorizationHeader());
				}
			};
		}
	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(name = "org.springframework.web.reactive.function.client.WebClient")
	static class WebClientRelay {

		@Bean
		@ConditionalOnMissingBean
		JwtRelayExchangeFilterFunction jwtRelayExchangeFilterFunction(OutboundTokenProvider tokens) {
			return new JwtRelayExchangeFilterFunction(tokens);
		}

		/** Applies to WebClient.Builder beans that Spring Boot creates. */
		@Bean
		WebClientCustomizer jwtRelayWebClientCustomizer(JwtRelayExchangeFilterFunction relay) {
			return builder -> builder.filter(relay);
		}
	}
}
