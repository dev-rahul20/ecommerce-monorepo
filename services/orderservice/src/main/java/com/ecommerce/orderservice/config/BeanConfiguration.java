package com.ecommerce.orderservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
    @Bean
    WebClient.Builder webClientBuilder(){
    	return WebClient.builder();
    }
	
}
