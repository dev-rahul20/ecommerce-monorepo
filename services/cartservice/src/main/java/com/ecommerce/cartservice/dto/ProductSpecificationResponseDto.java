package com.ecommerce.cartservice.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductSpecificationResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer productId;
	private String specKey;
	private String specValue;
	private LocalDateTime createdAt;

}
