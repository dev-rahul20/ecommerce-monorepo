package com.ecommerce.wishlistservice.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class ProductSpecificationResponseDto implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private Integer id;
    private Integer productId;
    private String specKey;
    private String specValue;
    private LocalDateTime createdAt;
}