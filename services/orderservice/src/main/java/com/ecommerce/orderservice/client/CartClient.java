package com.ecommerce.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CartClient {
	
	private final WebClient.Builder webClientBuilder;
	
	@Value("${cart.service.base-url}")
	private String baseUrl;

}
