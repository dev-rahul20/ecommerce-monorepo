package com.ecommerce.orderservice.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.orderservice.dto.OrderResponseDto;
import com.ecommerce.orderservice.entity.Order;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class OrderDaoImpl implements OrderDao {

	private final SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public Order saveOrder(Order order) {
		getSession().persist(order);
        return order;
	}
	
	@Override
	public List<Order> getAllOrders() {
		return getSession().createQuery("FROM Order", Order.class).getResultList();
	}
	
	@Override
	public Order getByOrderId(Long orderId) {
		 return getSession().get(Order.class, orderId);
	}
	
	@Override
	public List<Order> getByUserId(Integer userId) {
		
		return getSession().createQuery("FROM Order WHERE userId = :userId", Order.class)
						   .setParameter("userId", userId)
						   .getResultList();
	}

	@Override
	public void updateOrder(Order order) {
		getSession().merge(order);	
	}

}
