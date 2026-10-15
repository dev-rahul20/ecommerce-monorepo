package com.ecommerce.security.servlet;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.security.jwt.AuthenticatedUser;
import com.ecommerce.security.jwt.InvalidTokenException;
import com.ecommerce.security.jwt.JwtTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Reads "Authorization: Bearer &lt;jwt&gt;" and, when the token is valid, authenticates
 * the request as {@link AuthenticatedUser} with ROLE_* authorities. An invalid token
 * leaves the request anonymous; the authorization rules then answer 401 for anything
 * that is not public.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	static final String BEARER = "Bearer ";
	static final String ERROR_ATTRIBUTE = JwtAuthenticationFilter.class.getName() + ".error";

	private final JwtTokenService tokenService;

	public JwtAuthenticationFilter(JwtTokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (header != null && header.regionMatches(true, 0, BEARER, 0, BEARER.length())) {
			try {
				AuthenticatedUser user = tokenService.parse(header.substring(BEARER.length()).trim());
				List<SimpleGrantedAuthority> authorities = user.roles()
															   .stream()
															   .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
															   .toList();
				SecurityContext context = SecurityContextHolder.createEmptyContext();
				context.setAuthentication(UsernamePasswordAuthenticationToken.authenticated(user, null, authorities));
				SecurityContextHolder.setContext(context);
			} catch (InvalidTokenException ex) {
				SecurityContextHolder.clearContext();
				request.setAttribute(ERROR_ATTRIBUTE, ex.getMessage());
			}
		}
		chain.doFilter(request, response);
	}
}
