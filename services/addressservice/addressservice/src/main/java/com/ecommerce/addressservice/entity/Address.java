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

@Entity
@Table(name = "txn_adr")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adr_id")
    private Integer adrId;
    
    @Column(name = "adr_street", length = 255)
    private String adrStreet;

    @Column(name = "adr_area", length = 255)
    private String adrArea;

    @Column(name = "adr_landmark", length = 100, nullable = true, columnDefinition = "VARCHAR(100) DEFAULT ''")
    private String adrLandmark;

    @Column(name = "adr_pinno")
    private Integer adrPinno;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "adr_type", nullable = false, columnDefinition = "VARCHAR(100) DEFAULT 'SHIPPING'")
    private String adrType;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "last_updated_on")
    private LocalDateTime lastUpdatedOn;
    
    
}
