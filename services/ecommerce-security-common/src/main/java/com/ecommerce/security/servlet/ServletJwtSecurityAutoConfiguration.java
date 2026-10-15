package com.ecommerce.security.servlet;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.util.UrlPathHelper;

import com.ecommerce.security.JwtAutoConfiguration;
import com.ecommerce.security.jwt.JwtTokenService;
import com.ecommerce.security.jwt.PublicPathMatcher;

/**
 * Locks down every endpoint of a servlet service: a valid JWT is required except for
 * {@code ecommerce.security.public-paths}, /error and the actuator health/info probes.
 * Method security (@PreAuthorize) is enabled for finer role checks.
 */
@AutoConfiguration(after = JwtAutoConfiguration.class,
		before = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(name = "org.springframework.security.web.SecurityFilterChain")
@EnableMethodSecurity
public class ServletJwtSecurityAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(SecurityFilterChain.class)
	SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http, JwtTokenService tokenService,
			PublicPathMatcher publicPaths) throws Exception {

		JsonSecurityErrorHandler errors = new JsonSecurityErrorHandler();

		http.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.exceptionHandling(e -> e.authenticationEntryPoint(errors).accessDeniedHandler(errors))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(antMatcher("/error")).permitAll()
				.requestMatchers(antMatcher("/actuator/health"), antMatcher("/actuator/health/**"),
						antMatcher("/actuator/info")).permitAll()
				.requestMatchers(request -> publicPaths.matches(request.getMethod(),
						UrlPathHelper.defaultInstance.getPathWithinApplication(request))).permitAll()
				.anyRequest().authenticated())
			.addFilterBefore(new JwtAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	/** Stops Boot from creating its in-memory user with a generated password; JWT is the only login. */
	@Bean
	@ConditionalOnMissingBean(UserDetailsService.class)
	UserDetailsService noLocalUsers() {
		return username -> {
			throw new UsernameNotFoundException("Local users are disabled; authenticate with a JWT");
		};
	}
}
