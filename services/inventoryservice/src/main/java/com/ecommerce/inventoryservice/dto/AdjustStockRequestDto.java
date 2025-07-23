package com.ecommerce.inventoryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class AdjustStockRequestDto {

    @NotNull
    @Positive
    private Long productId;

    @NotNull
    @PositiveOrZero
    private Integer warehouseId;

    @NotNull
    private Integer changeQty; //Positive for restock, negative for deduction.

    @NotBlank
    private String reason;

    private String referenceId; //Optional reference, e.g., supplierReceiptId or orderId.

}   