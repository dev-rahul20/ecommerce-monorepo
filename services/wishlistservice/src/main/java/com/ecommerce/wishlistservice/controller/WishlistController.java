package com.ecommerce.wishlistservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.wishlistservice.entity.WishlistItem;
import com.ecommerce.wishlistservice.service.WishlistService;
import com.ecommerce.wishlistservice.util.WishlistResponce;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService service;

    @GetMapping("/details/{userId}")
    public Mono<WishlistResponce> getDetailedWishlist(@PathVariable @NotNull @Positive Integer userId) {
        
    	return service.getEnrichedWishlistByUser(userId)
        			  .collectList() //Convert Flux -> Mono
        			  .map(responce -> new WishlistResponce(true, HttpStatus.OK, "Detailed wishlist fetched Successfully!!", responce)); 
    }
    
    @PostMapping("/add")
    public WishlistResponce addItem(@RequestParam @NotNull @Positive Integer userId, @RequestParam @NotNull @Positive Integer productId) {
        WishlistItem item = service.addToWishlist(userId, productId);
        return new WishlistResponce(true, HttpStatus.OK, "Item added to wishlist Successfully!!", item);
    }

    @GetMapping("/{userId}")
    public WishlistResponce getItems(@PathVariable @NotNull @Positive Integer userId) {
    	List<WishlistItem> list = service.getWishlistByUser(userId);
        return new WishlistResponce(true, HttpStatus.OK, "Wishlist items fetched Successfully!!", list);
    }

    @DeleteMapping("/remove/{itemId}")
    public WishlistResponce removeItem(@PathVariable @NotBlank String itemId) {
    	service.removeFromWishlist(itemId);
        return new WishlistResponce(true, HttpStatus.OK, "Wishlist item removed Successfully!!", true);
    }
    
}
