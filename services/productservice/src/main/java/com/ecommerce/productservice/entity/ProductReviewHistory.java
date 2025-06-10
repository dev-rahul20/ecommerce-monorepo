package com.ecommerce.productservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ProductReviewHistory")
public class ProductReviewHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_history_id")
    private Integer id;

    @Column(name = "review_id", nullable = false)
    private Integer reviewId; // Reference to original review

    @Column(name = "user_id", nullable = false) // No FK to user_db
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer rating; // Copy of previous rating

    @Column(name = "review_text", columnDefinition = "TEXT")
    private String reviewText; // Copy of previous review text

    @Column(name = "edited_at", nullable = false, updatable = false)
    private LocalDateTime editedAt = LocalDateTime.now();
}