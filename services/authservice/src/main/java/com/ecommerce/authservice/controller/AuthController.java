package com.ecommerce.authservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.authservice.dto.AuthRequest;
import com.ecommerce.authservice.dto.AuthResponse;
import com.ecommerce.authservice.dto.AuthUserRequestDto;
import com.ecommerce.authservice.service.AuthService;
import com.ecommerce.authservice.util.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;
    
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid AuthRequest request) {
//        
//    	Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()) );
//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String token = jwtUtil.generateToken(userDetails);
//        return ResponseEntity.ok(new AuthResponse(token));
//    }
    
    /** This API Should be Called From User-Service or Directly */
    @PostMapping("/create")
    public AuthResponse createAuthUser(@RequestBody @Valid AuthUserRequestDto dto) {
		
    	Long createdAuthUserId = authService.createAuthUser(dto);
    	
    	return new AuthResponse(true, HttpStatus.CREATED, "User Auth Details Saved Successfully", createdAuthUserId);
    }
    
    /** This API Should be Called From User-Service or Directly */
    @PutMapping("/update")
    public AuthResponse updateAuthUser(@RequestBody @Valid AuthUserRequestDto dto) {
		
    	Long updatedAuthUserId = authService.updateAuthUser(dto);
    	
    	return new AuthResponse(true, HttpStatus.CREATED, "User Auth Details Saved Successfully", updatedAuthUserId);
    }
    
}
