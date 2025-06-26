package com.ecommerce.orderservice.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.orderservice.dao.OrderDao;
import com.ecommerce.orderservice.dto.OrderRequestDto;
import com.ecommerce.orderservice.dto.OrderResponseDto;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.enums.OrderStatus;
import com.ecommerce.orderservice.exception.OrderNotFoundException;
import com.ecommerce.orderservice.exception.OrderNotPlacedException;
import com.ecommerce.orderservice.util.OrderMapper;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderDao orderDao;
	private final OrderMapper orderMapper;
	private final ModelMapper modelMapper;

	@Override
	@Transactional
	public Order placeOrder(OrderRequestDto dto) {
		Order order = orderMapper.mapToEntity(dto);
		Order saveOrder = orderDao.saveOrder(order);

		Optional.ofNullable(saveOrder).filter(id -> saveOrder.getOrderId() > 0)
				.orElseThrow(() -> new OrderNotPlacedException("Error while placing Order"));

		return saveOrder;
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrderResponseDto> getAllOrders() {
		return orderDao.getAllOrders().stream()
				.map(order -> modelMapper.map(order, OrderResponseDto.class))
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public OrderResponseDto getByOrderId(@NotNull @Positive Long orderId) {
		Order order = orderDao.getByOrderId(orderId);

		Optional.ofNullable(order).orElseThrow(() -> new OrderNotFoundException("Order not found"));

		return modelMapper.map(order, OrderResponseDto.class);
	}

	@Override
	@Transactional
	public void cancelOrder(@NotNull @Positive Long orderId) {
		Order order = orderDao.getByOrderId(orderId);

		Optional.ofNullable(order).orElseThrow(() -> new OrderNotFoundException("Order not found"));

		switch (order.getOrderStatus()) {
		case DELIVERED, SHIPPED ->
			throw new IllegalStateException("Order cannot be cancelled once Shipped or Delivered.");
		case CANCELLED -> throw new IllegalStateException("Order is already Cancelled.");
		default -> {
			order.setOrderStatus(OrderStatus.CANCELLED);
			orderDao.updateOrder(order);
		}
		}
	}

	@Override
	@Transactional
	public List<OrderResponseDto> getOrdersByUserId(@NotNull @Positive Integer userId) {
		List<Order> orders = orderDao.getByUserId(userId);

		if (orders == null || orders.isEmpty()) {
		    throw new OrderNotFoundException("No orders found for user ID: " + userId);
		}

		return orders.stream().map(order -> modelMapper.map(order, OrderResponseDto.class)).toList();
	}

}
