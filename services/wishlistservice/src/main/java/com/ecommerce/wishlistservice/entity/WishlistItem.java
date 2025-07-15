package com.ecommerce.wishlistservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("WishlistItem")
public class WishlistItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String wishlistId;
    private Integer userId;
    private Integer productId;
    private LocalDateTime addedAt;
}
