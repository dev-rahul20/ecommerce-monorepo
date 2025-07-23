//package com.ecommerce.wishlistservice.kafka.consumer;
//
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//import com.ecommerce.wishlistservice.kafka.dto.WishlistEvent;
//
//@Component
//public class WishlistKafkaConsumer {
//
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    public WishlistKafkaConsumer(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    @KafkaListener(topics = "${wishlist.topic.update}", groupId = "wishlist-group")
//    public void handleProductUpdated(WishlistEvent event) {
//        String cacheKey = event.getProductId().toString();
//        redisTemplate.opsForHash().put("PRODUCT_CACHE", cacheKey, null); // or fetch latest & update
//        // Optionally refresh TTL or delete old cache entry
//    }
//
//    @KafkaListener(topics = "${wishlist.topic.remove}", groupId = "wishlist-group")
//    public void handleProductRemoved(WishlistEvent event) {
//        String cacheKey = event.getProductId().toString();
//        redisTemplate.opsForHash().delete("PRODUCT_CACHE", cacheKey);
//    }
//}
