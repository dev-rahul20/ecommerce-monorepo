//package com.ecommerce.wishlistservice.kafka.producer;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import com.ecommerce.wishlistservice.kafka.dto.WishlistEvent;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Component
//public class WishlistKafkaProducer {
//
//    private final KafkaTemplate<String, WishlistEvent> kafkaTemplate;
//    
//    @Value("${wishlist.topic.update}")
//    private String updateTopic;
//    
//    @Value("${wishlist.topic.remove}")
//    private String removeTopic;
//    
//
//    public void publishUpdate(WishlistEvent event) {
//        kafkaTemplate.send(updateTopic, event.getProductId().toString(), event);
//    }
//
//    public void publishRemoval(WishlistEvent event) {
//        kafkaTemplate.send(removeTopic, event.getProductId().toString(), event);
//    }
//}
