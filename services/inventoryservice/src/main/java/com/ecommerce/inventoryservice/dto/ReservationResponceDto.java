package com.ecommerce.inventoryservice.dto;

import com.ecommerce.inventoryservice.enums.ReservationStatus;

import lombok.Data;

@Data
public class ReservationResponceDto {

    private Long reservationId;
    private ReservationStatus status;
}
