package com.ecommerce.notification.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ecommerce.notification.domain.Channel;
import com.ecommerce.notification.domain.NotificationPriority;
import com.ecommerce.notification.domain.NotificationPriority;
import com.ecommerce.notification.domain.NotificationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Integer userId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 16)
	private Channel channel;

	@Column(length = 255)
	private String subject;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String message;

	@Column(name = "to_address", length = 255)
	private String toAddress;

	@Column(name = "to_phone", length = 32)
	private String toPhone;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 16)
	private NotificationStatus status;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 16)
	private NotificationPriority priority;

	@Column(name = "error_message", length = 512)
	private String errorMessage;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt = LocalDateTime.now();

}
