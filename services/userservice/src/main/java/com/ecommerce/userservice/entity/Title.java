package com.ecommerce.userservice.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mst_title")
public class Title {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title_id")
    private Integer titleId;
    
    @Column(name = "title_type", length = 255)
    private String titleType;
    
    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "last_updated_on")
    private LocalDateTime lastUpdatedOn;

}
