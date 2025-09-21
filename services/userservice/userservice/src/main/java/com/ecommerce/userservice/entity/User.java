package com.ecommerce.userservice.entity;

import java.time.Instant;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ecommerce.userservice.constants.AuthSyncStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "txn_user")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    
    @ManyToOne
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender genderId;
    
    @ManyToOne
    @JoinColumn(name = "title_id", nullable = false)
    private Title titleId;
    
    @Column(name = "user_age")
    private Integer userAge;
    
    @Column(name = "user_first_name")
    private String userFirstName;
    
    @Column(name = "user_last_name")
    private String userLastName;
    
    @Column(name = "user_middle_name")
    private String userMiddleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_sync_status", nullable = false)
    private AuthSyncStatus authSyncStatus = AuthSyncStatus.NOT_STARTED;
    
    @Column(name = "auth_retry_count", nullable = false)
    private Integer authRetryCount = 0;

    @Column(name = "auth_last_attempt_at")
    private Instant authLastAttemptAt;
    
    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "last_updated_on")
    private LocalDateTime lastUpdatedOn;


}
