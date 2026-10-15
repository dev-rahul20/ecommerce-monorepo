package com.ecommerce.userservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecommerce.security.relay.JwtRelayExchangeFilterFunction;

@Configuration
public class BeanConfiguration {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
	
    @Bean
    WebClient authWebClient(@Value("${auth.service.baseurl}") String authServiceBaseUrl,
            JwtRelayExchangeFilterFunction jwtRelay) {
        return WebClient.builder()
                .baseUrl(authServiceBaseUrl)
                // Registration runs on a background thread, so this sends a service token
                .filter(jwtRelay)
                // avoid large default memory limits if you return big payloads
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();
    }
}
