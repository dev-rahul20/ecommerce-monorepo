package com.ecommerce.inventoryservice.inventory.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ecommerce.inventoryservice.dto.AdjustStockRequestDto;
import com.ecommerce.inventoryservice.dto.InventoryItemDto;
import com.ecommerce.inventoryservice.dto.ReservationResponceDto;
import com.ecommerce.inventoryservice.dto.ReserveStockRequestDto;
import com.ecommerce.inventoryservice.exception.InventoryItemNotFoundException;
import com.ecommerce.inventoryservice.inventory.dao.InventoryDao;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InventoryServiceImpl implements InventoryService {

	private final InventoryDao dao;
	private final ModelMapper modelMapper;
	
	@Override
	public List<InventoryItemDto> getInventoryItems(Integer productId) {
		
		List<InventoryItemDto> list = dao.getInventoryItems(productId);
		
		return Optional.ofNullable(list)
					   .orElseThrow(() -> new InventoryItemNotFoundException("Inventory Items not found"));
	}

	@Override
	public int getAvailableQuantity(Integer productId) {
		
		return 0;
	}

	@Override
	public ReservationResponceDto reserveStock(@Valid ReserveStockRequestDto request) {
		
		return null;
	}

	@Override
	public void commitReservation(Integer reservationId) {
		
		
	}

	@Override
	public void cancelReservation(Integer reservationId) {
		
		
	}

	@Override
	public void adjustStock(@Valid AdjustStockRequestDto request) {
		
		
	}

	
	
}
