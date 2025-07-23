package com.ecommerce.inventoryservice.inventory.service;

import java.util.List;

import com.ecommerce.inventoryservice.dto.AdjustStockRequestDto;
import com.ecommerce.inventoryservice.dto.InventoryItemDto;
import com.ecommerce.inventoryservice.dto.ReservationResponceDto;
import com.ecommerce.inventoryservice.dto.ReserveStockRequestDto;

import jakarta.validation.Valid;

public interface InventoryService {

	List<InventoryItemDto> getInventoryItems(Integer productId);

	int getAvailableQuantity(Integer productId);

	ReservationResponceDto reserveStock(@Valid ReserveStockRequestDto request);

	void commitReservation(Integer reservationId);

	void cancelReservation(Integer reservationId);

	void adjustStock(@Valid AdjustStockRequestDto request);

}
