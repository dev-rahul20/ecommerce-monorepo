package com.ecommerce.orderservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.orderservice.dto.OrderRequestDto;
import com.ecommerce.orderservice.dto.OrderResponseDto;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.service.OrderService;
import com.ecommerce.orderservice.util.OrderResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController {
	
    private final OrderService orderService;
	
	@PostMapping("place-order")
	public OrderResponse placeOrder(@Valid @RequestBody OrderRequestDto dto) {
		Order order = orderService.placeOrder(dto);
		Map<String, Object> data = new HashMap<>();
		data.put("orderId", order.getOrderId());
		data.put("OrderStatus", order.getOrderStatus());
		data.put("TotalAmount", order.getTotalAmount());
		return new OrderResponse(true, HttpStatus.OK, "Order Placed Successfully!!!", data);
	}
	
	@GetMapping
    public OrderResponse getAllOrders() {
        List<OrderResponseDto> list = orderService.getAllOrders();
        return new OrderResponse(true, HttpStatus.OK, "Successfully fetched data!!!", list);
    }
	
	@GetMapping("/{orderId}")
    public OrderResponse getByOrderId(@PathVariable @NotNull @Positive Long orderId) {
		OrderResponseDto order = orderService.getByOrderId(orderId);
        return new OrderResponse(true, HttpStatus.OK, "Order found!!!", order);
    }
	
	@PutMapping("/{orderId}/cancel")
	public OrderResponse cancelOrder(@PathVariable @NotNull @Positive Long orderId) {
	    orderService.cancelOrder(orderId);
	    return new OrderResponse(true, HttpStatus.OK, "Order cancelled!!!", null);
	}
	
	@GetMapping("/user/{userId}")
	public OrderResponse getOrdersByUserId(@PathVariable @NotNull @Positive Integer userId) {
	    List<OrderResponseDto> orders = orderService.getOrdersByUserId(userId);
	    return new OrderResponse(true, HttpStatus.OK, "Order Fetched successfully!!!", orders);
	}



}
