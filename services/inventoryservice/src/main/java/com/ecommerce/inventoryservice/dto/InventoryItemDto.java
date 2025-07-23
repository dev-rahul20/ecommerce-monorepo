package com.ecommerce.inventoryservice.dto;

import lombok.Data;

@Data
public class InventoryItemDto {

    private Long inventoryId;
    private Long productId;
    private Integer warehouseId;
    private Integer quantityOnHand;
    private Integer quantityReserved;

}
