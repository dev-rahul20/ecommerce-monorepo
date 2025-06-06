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
@Table(name = "txn_user")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    
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

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "last_updated_on")
    private LocalDateTime lastUpdatedOn;


}
