package com.ecommerce.orderservice.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ecommerce.orderservice.dto.OrderItemDto;
import com.ecommerce.orderservice.dto.OrderRequestDto;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.entity.OrderItem;
import com.ecommerce.orderservice.entity.OrderTotal;

@Component
public class OrderMapper {
	
	public Order mapToEntity(OrderRequestDto dto) {
		
		Order order = new Order();
		
		order.setUserId(dto.getUserId());
		order.setCurrency(dto.getCurrency());
		
		List<OrderItem> items = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;
        
        for (OrderItemDto itemDto : dto.getItems()) {
        	
        	OrderItem orderItem = new OrderItem();
        	orderItem.setProductId(itemDto.getProductId());
        	orderItem.setProductName(itemDto.getProductName());
        	orderItem.setProductPrice(itemDto.getProductPrice());
        	orderItem.setQuantity(itemDto.getQuantity());
        	
            BigDecimal lineTotal = itemDto.getProductPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            orderItem.setLineTotal(lineTotal);
            orderItem.setOrder(order);
        	
            subtotal = subtotal.add(lineTotal);
            items.add(orderItem);
        	
        }

        order.setOrderItems(items);
        
        BigDecimal delivery = BigDecimal.valueOf(50);
        BigDecimal tax = subtotal.multiply(BigDecimal.valueOf(0.18));
        BigDecimal total = subtotal.add(delivery).add(tax);
        
        OrderTotal orderTotal = new OrderTotal();
        orderTotal.setOrder(order);
        orderTotal.setSubtotal(subtotal);
        orderTotal.setDeliveryCharge(delivery);
        orderTotal.setTax(tax);
        orderTotal.setTotalAmount(total);

        order.setOrderTotal(orderTotal);
        order.setTotalAmount(total);

        return order;



		
	}

}
