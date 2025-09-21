package com.ecommerce.authservice.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@Builder
public class UserRole {

	@EmbeddedId
	private UserRoleId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("authUserId")
	@JoinColumn(name = "auth_user_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private AuthUser authUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("roleId")
	@JoinColumn(name = "role_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Role role;

	@Column(name = "assigned_at", nullable = false)
	private LocalDateTime assignedAt;
}