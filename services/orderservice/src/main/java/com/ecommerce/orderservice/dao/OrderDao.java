package com.ecommerce.orderservice.dao;

import java.util.List;

import com.ecommerce.orderservice.dto.OrderResponseDto;
import com.ecommerce.orderservice.entity.Order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface OrderDao {

	Order saveOrder(Order order);

	List<Order> getAllOrders();

	Order getByOrderId(Long orderId);

	List<Order> getByUserId(Integer userId);

	void updateOrder(Order order);

}
