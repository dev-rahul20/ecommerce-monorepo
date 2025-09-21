package com.ecommerce.authservice.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "processed_event")
@Getter
@Setter
@Builder
public class ProcessedEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "processed_event_id")
	private Long processedEventId;

	@Column(name = "event_id", nullable = false, unique = true, length = 36)
	private String eventId;

	@CreationTimestamp
	@Column(name = "processed_at", nullable = false, updatable = false)
	private LocalDateTime processedAt;

	@Column(name = "source")
	private String source;
}