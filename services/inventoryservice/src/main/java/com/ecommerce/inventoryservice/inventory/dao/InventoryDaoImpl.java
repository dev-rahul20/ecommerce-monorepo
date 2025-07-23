package com.ecommerce.inventoryservice.inventory.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.inventoryservice.dto.InventoryItemDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class InventoryDaoImpl implements InventoryDao {

	private final SessionFactory factory;
	
	private Session getSession() {
		return factory.getCurrentSession();
	}
	
	@Override
	public List<InventoryItemDto> getInventoryItems(Integer productId) {
		return getSession().createQuery("From InventoryItem AS i WHERE i.productId = :productId", InventoryItemDto.class)
						   .setParameter("productId", productId)
						   .list();
	}

}
