package com.ecommerce.addressservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.addressservice.util.UserResponse;

@FeignClient("USER-SERVICE")
public interface UserClient {
	
	@GetMapping("/users/{userId}")
	UserResponse getUserByUserId(@PathVariable Integer userId);

}
