package com.ecommerce.notification.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.notification.channel.EmailSender;
import com.ecommerce.notification.channel.SmsSender;
import com.ecommerce.notification.domain.Channel;
import com.ecommerce.notification.domain.NotificationStatus;
import com.ecommerce.notification.dto.NotificationResponseDto;
import com.ecommerce.notification.dto.SendNotificationRequest;
import com.ecommerce.notification.entity.Notification;
import com.ecommerce.notification.repository.NotificationRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepo repo;
	private final EmailSender emailSender;
	private final SmsSender smsSender;
	private final ModelMapper mapper;

	@Override
	@Transactional
	public NotificationResponseDto send(SendNotificationRequest req) {
		// 1) Persist as PENDING
		Notification n = Notification.builder().userId(req.getUserId()).channel(req.getChannel()).priority(req.getPriority())
				.toAddress(req.getToAddress()).toPhone(req.getToPhone()).subject(req.getSubject())
				.message(req.getMessage()).status(NotificationStatus.PENDING).build();
		n = repo.save(n);

		// 2) Try to send
		try {
			if (req.getChannel() == Channel.EMAIL) {
				if (req.getToAddress() == null || req.getToAddress().isBlank()) {
					throw new IllegalArgumentException("toAddress required for EMAIL");
				}
				emailSender.send(req.getToAddress(), req.getSubject(), req.getMessage());
			} else {
				if (req.getToPhone() == null || req.getToPhone().isBlank()) {
					throw new IllegalArgumentException("toPhone required for SMS");
				}
				smsSender.send(req.getToPhone(), req.getMessage());
			}
			n.setStatus(NotificationStatus.SENT);
			n.setErrorMessage(null);
		} catch (Exception e) {
			n.setStatus(NotificationStatus.FAILED);
			n.setErrorMessage(e.getMessage());
		}

		// 3) Save final status
		n = repo.save(n);
		return mapper.map(n, NotificationResponseDto.class);
	}

	@Override
	public Page<NotificationResponseDto> listByUser(Integer userId, Pageable pageable) {
		return repo.findByUserId(userId, pageable).map(n -> mapper.map(n, NotificationResponseDto.class));
	}

	@Override
	public NotificationResponseDto findById(Long id) {
		Notification n = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found"));
		return mapper.map(n, NotificationResponseDto.class);
	}

	@Override
	@Transactional
	public void markAsRead(Long id) {
		Notification n = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found"));
		n.setStatus(NotificationStatus.READ);
		repo.save(n);
	}

}
