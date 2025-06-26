package com.ecommerce.orderservice.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequestDto {

	@NotNull
	private Integer userId;

	@NotEmpty
	private String currency;

	@NotEmpty
	private List<OrderItemDto> items;

}
