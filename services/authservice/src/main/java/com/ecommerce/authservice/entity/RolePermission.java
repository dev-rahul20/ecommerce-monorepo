package com.ecommerce.authservice.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "role_permission")
@Getter
@Setter
@Builder
public class RolePermission {

	@EmbeddedId
	private RolePermissionId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("roleId")
	@JoinColumn(name = "role_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("permissionId")
	@JoinColumn(name = "permission_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Permission permission;

	@Column(name = "assigned_at", nullable = false)
	private LocalDateTime assignedAt;
}