package com.ecommerce.notification.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.notification.dto.NotificationResponseDto;
import com.ecommerce.notification.dto.SendNotificationRequest;
import com.ecommerce.notification.service.NotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService service;

	@PostMapping("/send")
	public ResponseEntity<NotificationResponseDto> send(@RequestBody @Valid SendNotificationRequest req) {
		return ResponseEntity.ok(service.send(req));
	}

	@GetMapping
	public ResponseEntity<Page<NotificationResponseDto>> list(@RequestParam Integer userId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {

		return ResponseEntity.ok(service.listByUser(userId, PageRequest.of(page, size)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<NotificationResponseDto> find(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PutMapping("/{id}/read")
	public ResponseEntity<Void> markRead(@PathVariable Long id) {
		service.markAsRead(id);
		return ResponseEntity.noContent().build();
	}

}
