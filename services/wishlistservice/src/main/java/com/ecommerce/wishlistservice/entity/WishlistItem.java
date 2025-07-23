package com.ecommerce.wishlistservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
@RedisHash("WishlistItem") 
public class WishlistItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String wishlistId;
    
    @Indexed
    private Integer userId;
    
    @Indexed
    private Integer productId;
    
    private LocalDateTime addedAt;
}
