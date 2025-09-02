package com.ecommerce.notification.dto;

import java.time.LocalDateTime;

import com.ecommerce.notification.domain.Channel;
import com.ecommerce.notification.domain.NotificationPriority;
import com.ecommerce.notification.domain.NotificationStatus;

import lombok.*;


@Getter
@Setter
@Builder
public class NotificationResponseDto {
	private Long id;
	private Integer userId;
	private Channel channel;
	private NotificationStatus status;
	private NotificationPriority priority;
	private String toAddress;
	private String toPhone;
	private String subject;
	private String message;
	private String errorMessage;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
