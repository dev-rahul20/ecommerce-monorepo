package com.ecommerce.notification.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.notification.entity.Notification;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long>{
	
	  Page<Notification> findByUserId(Integer userId, Pageable p);


}
