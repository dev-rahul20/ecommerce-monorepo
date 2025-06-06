package com.ecommerce.userservice.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "txn_mobile")
public class Mobile {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mobile_id")
    private Integer mobileId;
    
    @Column(name = "mobile_type", length = 255)
    private String mobileType;
    
    @Column(name = "mobile_num", length = 255)
    private String mobileNum;
    
    @ManyToOne
    @JoinColumn(name = "isd_id", nullable = false)
    private Isd isdId;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "last_updated_on")
    private LocalDateTime lastUpdatedOn;

}
