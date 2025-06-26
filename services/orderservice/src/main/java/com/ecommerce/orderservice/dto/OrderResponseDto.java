package com.ecommerce.orderservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private Integer userId;
    private String orderStatus;
    private String currency;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private List<OrderItemDto> items;
    private OrderTotalDto totalBreakdown;
}
