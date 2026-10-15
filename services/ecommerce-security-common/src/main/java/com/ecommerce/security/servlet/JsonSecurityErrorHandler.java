package com.ecommerce.security.servlet;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.ecommerce.security.web.SecurityErrorBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Writes 401/403 as JSON instead of the default HTML/empty bodies. */
public class JsonSecurityErrorHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		Object tokenError = request.getAttribute(JwtAuthenticationFilter.ERROR_ATTRIBUTE);
		String message = tokenError != null ? tokenError.toString() : "Authentication required";
		response.setHeader("WWW-Authenticate", "Bearer");
		write(response, HttpStatus.UNAUTHORIZED, message);
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException {
		write(response, HttpStatus.FORBIDDEN, "Access denied");
	}

	private static void write(HttpServletResponse response, HttpStatus status, String message) throws IOException {
		response.setStatus(status.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getOutputStream().write(SecurityErrorBody.json(status.name(), message));
	}
}
