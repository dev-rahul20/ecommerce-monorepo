package com.ecommerce.authservice.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class RolePermissionId implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long roleId;
	private Long permissionId;
}