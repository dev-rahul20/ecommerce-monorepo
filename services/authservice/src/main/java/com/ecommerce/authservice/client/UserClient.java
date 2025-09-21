//// package com.ecommerce.authservice.client
//package com.ecommerce.authservice.client;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import com.ecommerce.authservice.config.UserFeignConfig;
//import com.ecommerce.authservice.dto.UserProfileDto;
//
//@FeignClient(
//    name = "user-service",
//    url = "${user.service.url}",
//    configuration = UserFeignConfig.class,
//    fallbackFactory = UserClientFallbackFactory.class
//)
//public interface UserClient {
//    
//	@GetMapping("/api/users/{username}")
//    UserProfileDto getByUsername(@PathVariable("username") String username);
//}
