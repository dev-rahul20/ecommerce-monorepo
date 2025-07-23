package com.ecommerce.inventoryservice.inventory.dao;

import java.util.List;

import com.ecommerce.inventoryservice.dto.InventoryItemDto;

public interface InventoryDao {

	List<InventoryItemDto> getInventoryItems(Integer productId);

}
