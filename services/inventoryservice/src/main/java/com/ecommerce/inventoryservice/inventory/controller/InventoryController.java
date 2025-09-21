package com.ecommerce.inventoryservice.inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.inventoryservice.dto.AdjustStockRequestDto;
import com.ecommerce.inventoryservice.dto.InventoryItemDto;
import com.ecommerce.inventoryservice.dto.ReservationResponceDto;
import com.ecommerce.inventoryservice.dto.ReserveStockRequestDto;
import com.ecommerce.inventoryservice.inventory.service.InventoryService;
import com.ecommerce.inventoryservice.util.InventoryResponce;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("inventory")
public class InventoryController {

	private final InventoryService service;

	/**
	 * Get all inventory entries for a product across warehouses.
	 */
	@GetMapping("/products/{productId}")
	public InventoryResponce getInventoryItems(@PathVariable Integer productId) {
		List<InventoryItemDto> items = service.getInventoryItems(productId);
		return new InventoryResponce(true, HttpStatus.OK, "Inventory Items Fetched Successfully", items);
	}

	/**
	 * Get total available quantity (onHand − reserved) for a product.
	 */
	@GetMapping("/products/{productId}/available")
	public InventoryResponce getAvailableQuantity(@PathVariable Integer productId) {
		int available = service.getAvailableQuantity(productId);
		return new InventoryResponce(true, HttpStatus.OK, "Available Product Quantity Fetched Successfully", available);
	}

	/**
	 * Reserve stock for an order (creates PENDING reservation).
	 */
	@PostMapping("/reserve")
	public InventoryResponce reserveStock(@Valid @RequestBody ReserveStockRequestDto request) {
		ReservationResponceDto dto = service.reserveStock(request);
		return new InventoryResponce(true, HttpStatus.CREATED, "Reserve Stock Fetched Successfully", dto);
	}

	/**
	 * Commit a pending reservation (moves reserved → onHand adjustment).
	 */
	@PostMapping("/reservations/{reservationId}/commit")
	public InventoryResponce commitReservation(@PathVariable Integer reservationId) {
		service.commitReservation(reservationId);
		return new InventoryResponce(true, HttpStatus.CREATED, "Pending Reservations Commit Successfully", null);
	}

	/**
	 * Cancel a pending reservation (releases held stock).
	 */
	@PostMapping("/reservations/{reservationId}/cancel")
	public InventoryResponce cancelReservation(@PathVariable Integer reservationId) {
		service.cancelReservation(reservationId);
		return new InventoryResponce(true, HttpStatus.CREATED, "Pending Reservations Canceled Successfully", null);
	}

	/**
	 * Adjust stock manually or via restock/return events.
	 */
	@PostMapping("/adjust")
	public InventoryResponce adjustStock(@Valid @RequestBody AdjustStockRequestDto request) {
		service.adjustStock(request);
		return new InventoryResponce(true, HttpStatus.CREATED, "Stock Adjust Successfully", null);
	}

}
