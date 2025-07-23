package com.ecommerce.inventoryservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class ReserveStockRequestDto {

    @NotNull
    @Positive
    private Long orderId;

    @NotNull
    @Positive
    private Long productId;

    @NotNull
    @Positive
    private Integer warehouseId;

    @NotNull
    @Positive
    private Integer quantity;

}
