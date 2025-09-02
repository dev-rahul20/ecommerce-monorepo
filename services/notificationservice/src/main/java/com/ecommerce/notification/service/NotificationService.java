package com.ecommerce.notification.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.notification.dto.NotificationResponseDto;
import com.ecommerce.notification.dto.SendNotificationRequest;

public interface NotificationService {

	NotificationResponseDto send(SendNotificationRequest req);

	Page<NotificationResponseDto> listByUser(Integer userId, Pageable pageable);

	NotificationResponseDto findById(Long id);

	void markAsRead(Long id);

}
