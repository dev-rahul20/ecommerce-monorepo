package com.ecommerce.authservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.authservice.dto.AuthRequest;
import com.ecommerce.authservice.dto.AuthResponse;
import com.ecommerce.authservice.dto.AuthUserRequestDto;
import com.ecommerce.authservice.dto.RefreshRequest;
import com.ecommerce.authservice.dto.TokenResponse;
import com.ecommerce.authservice.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	/** Public. */
	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody @Valid AuthRequest request, HttpServletRequest http,
			@RequestHeader(value = HttpHeaders.USER_AGENT, required = false) String userAgent) {
		return ResponseEntity.ok(authService.login(request.getUsername(), request.getPassword(), http.getRemoteAddr(), userAgent));
	}

	/** Public: the refresh token is the credential. */
	@PostMapping("/refresh")
	public ResponseEntity<TokenResponse> refresh(@RequestBody @Valid RefreshRequest request, HttpServletRequest http,
			@RequestHeader(value = HttpHeaders.USER_AGENT, required = false) String userAgent) {
		return ResponseEntity.ok(authService.refresh(request.getRefreshToken(), http.getRemoteAddr(), userAgent));
	}

	/** Public: revokes the refresh token it is given. */
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestBody @Valid RefreshRequest request) {
		authService.logout(request.getRefreshToken());
		return ResponseEntity.noContent().build();
	}

	/** Called by user-service with a service token when a user registers. */
	@PreAuthorize("hasRole('SERVICE')")
	@PostMapping("/create")
	public AuthResponse createAuthUser(@RequestBody @Valid AuthUserRequestDto dto) {

		Long createdAuthUserId = authService.createAuthUser(dto);

		return new AuthResponse(true, HttpStatus.CREATED, "User Auth Details Saved Successfully", createdAuthUserId);
	}

	@PreAuthorize("hasRole('SERVICE')")
	@PutMapping("/update")
	public AuthResponse updateAuthUser(@RequestBody @Valid AuthUserRequestDto dto) {

		Long updatedAuthUserId = authService.updateAuthUser(dto);

		return new AuthResponse(true, HttpStatus.OK, "User Auth Details Updated Successfully", updatedAuthUserId);
	}

}
