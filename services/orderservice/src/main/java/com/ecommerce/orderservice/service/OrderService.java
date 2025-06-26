package com.ecommerce.orderservice.service;

import java.util.List;

import com.ecommerce.orderservice.dto.OrderRequestDto;
import com.ecommerce.orderservice.dto.OrderResponseDto;
import com.ecommerce.orderservice.entity.Order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface OrderService {
	
    Order placeOrder(OrderRequestDto orderRequestDto);

	List<OrderResponseDto> getAllOrders();

	OrderResponseDto getByOrderId(@NotNull @Positive @NotNull @Positive Long orderId);

	void cancelOrder(@NotNull @Positive Long orderId);

	List<OrderResponseDto> getOrdersByUserId(@NotNull @Positive Integer userId);


}
