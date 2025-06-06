package com.ecommerce.addressservice.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Getter
@Setter
@Entity
@Table(name = "mst_state")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    private Integer stateId;

    @Column(name = "state_name", nullable = false, length = 50)
    private String stateName;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedOn;

}
