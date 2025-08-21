package com.ecommerce.cartservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
/** TTL for Carts, This prevents abandoned carts from staying in Redis forever. This is 30 days  */
@RedisHash(value = "CartItem", timeToLive = 2592000)
public class CartItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String cartItemId;

	@Indexed
	private Integer userId;

	@Indexed
	private Integer productId;

	private Integer quantity;

	private LocalDateTime addedAt;
}
