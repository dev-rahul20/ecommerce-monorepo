package com.ecommerce.wishlistservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        
    	 RedisTemplate<String, Object> template = new RedisTemplate<>();
         template.setConnectionFactory(factory);
         template.setKeySerializer(new StringRedisSerializer());
         template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        
         return template;
    }
    
    @Bean
    WebClient.Builder webClientBuilder(){
    	return WebClient.builder();
    }
    
    
}
