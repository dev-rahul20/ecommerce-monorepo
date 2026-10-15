package com.ecommerce.orderservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecommerce.security.relay.JwtRelayExchangeFilterFunction;

@Configuration
public class BeanConfiguration {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
    @Bean
    WebClient.Builder webClientBuilder(JwtRelayExchangeFilterFunction jwtRelay) {
		// Forwards the caller's JWT (or a service token) to the services we call
		return WebClient.builder().filter(jwtRelay);
    }
	
}
