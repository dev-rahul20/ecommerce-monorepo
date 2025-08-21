package com.ecommerce.cartservice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.cartservice.entity.CartItem;

@Repository
public interface CartRepository extends CrudRepository<CartItem, String>{

	List<CartItem> findByUserId(Integer userId);

	boolean existsByUserIdAndProductId(Integer userId, Integer productId);

	void deleteByUserId(Integer userId);

}
