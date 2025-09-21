//// package com.ecommerce.authservice.client
//package com.ecommerce.authservice.client;
//
//import com.ecommerce.authservice.dto.UserProfileDto;
//import feign.FeignException;
//import feign.hystrix.FallbackFactory; // or use resilience4j; this example is generic
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserClientFallbackFactory implements FallbackFactory<UserClient> {
//
//    @Override
//    public UserClient create(Throwable cause) {
//        return new UserClient() {
//            @Override
//            public UserProfileDto getByUsername(String username) {
//                // return null or a safe default; log the cause
//                // In production you might return cached profile or a compact DTO
//                return null;
//            }
//        };
//    }
//}
