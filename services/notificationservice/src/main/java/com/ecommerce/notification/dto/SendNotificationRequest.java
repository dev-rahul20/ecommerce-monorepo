package com.ecommerce.notification.dto;

import com.ecommerce.notification.domain.Channel;
import com.ecommerce.notification.domain.NotificationPriority;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendNotificationRequest {

	@NotNull
	private Integer userId;
	@NotNull
	private Channel channel;
	@NotNull
	private NotificationPriority priority = NotificationPriority.NORMAL;

	@Email(message = "Invalid email")
	private String toAddress;
	private String subject;

	// SMS
	private String toPhone;

	@NotBlank
	private String message;

}
