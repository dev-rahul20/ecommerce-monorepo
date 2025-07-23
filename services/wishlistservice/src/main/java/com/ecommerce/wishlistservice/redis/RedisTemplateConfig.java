package com.ecommerce.wishlistservice.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

public class RedisTemplateConfig {

    private static volatile RedisTemplate<String, Object> instance;

    private RedisTemplateConfig() { }

    public static RedisTemplate<String, Object> getInstance(RedisConnectionFactory factory) {
        if (instance == null) {
            synchronized (RedisTemplateConfig.class) {
                if (instance == null) {
                    instance = buildRedisTemplate(factory);
                }
            }
        }
        return instance;
    }

    private static RedisTemplate<String, Object> buildRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory); 
        template.setKeySerializer(new StringRedisSerializer()); 
        template.setHashKeySerializer(new StringRedisSerializer()); 
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        template.afterPropertiesSet();
        return template;
    }
}
