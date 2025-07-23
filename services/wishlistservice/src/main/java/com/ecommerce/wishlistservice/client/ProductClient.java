package com.ecommerce.wishlistservice.client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecommerce.wishlistservice.dto.ProductDTO;
import com.ecommerce.wishlistservice.dto.ProductResponseWrapper;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ProductClient {

    private final WebClient.Builder webClientBuilder;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${product.service.base-url}")
    private String baseUrl;

    @Value("${product-api.get-by-product-id}")
    private String productByIdApi;

    @Value("${redis.cache.product}")
    private String cacheName;

    public Mono<ProductDTO> getProductById(Integer productId) {
    	
        ProductDTO cached = (ProductDTO) redisTemplate.opsForHash().get(cacheName, productId.toString());

        if (cached != null) {
            return Mono.just(cached);
        }

        return webClientBuilder.baseUrl(baseUrl)
                               .build()
                               .get()
                               .uri(productByIdApi, productId)
                               .retrieve()
                               .bodyToMono(ProductResponseWrapper.class)
                               .map(wrapper -> {
                                   ProductDTO dto = wrapper.getData();
                                   if (dto != null) {
                                       redisTemplate.opsForHash().put(cacheName, productId.toString(), dto);
                                       redisTemplate.expire(cacheName, Duration.ofMinutes(0));
                                   }
                                   return dto;
                               });
    }
}
