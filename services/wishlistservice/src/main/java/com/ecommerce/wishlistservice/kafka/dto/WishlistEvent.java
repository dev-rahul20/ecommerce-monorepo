package com.ecommerce.wishlistservice.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistEvent {
	
    private Integer productId;
    private Integer userId;
    private String action; // e.g. "ADDED", "REMOVED", "UPDATED"

}
