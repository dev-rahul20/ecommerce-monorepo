package com.ecommerce.authservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "auth_user")
@Getter
@Setter
@Builder
public class AuthUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auth_user_id")
	private Long authUserId;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "user_service_id", unique = true)
	private Long userServiceId;

	@Column(name = "email", unique = true)
	private String email;

	@Builder.Default
	@Column(name = "email_verified", nullable = false)
	private Boolean emailVerified = false;

	@Column(name = "password_hash", nullable = false, columnDefinition = "TEXT")
	private String passwordHash;

	@Builder.Default
	@Column(name = "enabled", nullable = false)
	private Boolean enabled = true;

	@Builder.Default
	@Column(name = "locked", nullable = false)
	private Boolean locked = false;

	@Builder.Default
	@Column(name = "failed_attempts", nullable = false)
	private Integer failedAttempts = 0;

	@Builder.Default
	@Column(name = "profile_version", nullable = false)
	private Long profileVersion = 0L;
}