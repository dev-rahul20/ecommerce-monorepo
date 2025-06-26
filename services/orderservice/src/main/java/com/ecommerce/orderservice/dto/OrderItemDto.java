package com.ecommerce.orderservice.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

	@NotNull
	private Long productId;

	@NotNull
	private String productName;

	@NotNull
	private BigDecimal productPrice;

	@NotNull
	private Integer quantity;

}
