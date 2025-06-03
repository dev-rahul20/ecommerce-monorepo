package com.ecommerce.addressservice.address.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecommerce.addressservice.entity.Address;

@Repository
public class AddressDaoImpl implements AddressDao {
	
	@Autowired
    private SessionFactory sessionFactory;
	
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
	
	@Override
	public List<Address> getAllAddress() {
		return getSession().createQuery("FROM Address", Address.class).getResultList();
	}

	@Override
	public Address getAddressByAddressId(Integer addressId) {
		
		 return getSession().createQuery("FROM Address WHERE addressId = :addressId", Address.class)
                 			.setParameter("addressId", addressId)
                 			.uniqueResult();
	}

	@Override
	public Address getAddressByUserId(Integer userId) {
		
		return getSession().createQuery("FROM Address WHERE userId = :userId", Address.class)
     					   .setParameter("userId", userId)
     					   .uniqueResult();
	}
	
	@Override
	public Address getAddressByAddressIdAndUserId(Integer addressId, Integer userId) {
		
		return getSession().createQuery("FROM Address WHERE addressId = :addressId, userId = :userId", Address.class)
						   .setParameter("addressId", addressId)
						   .setParameter("userId", userId)
				   		   .uniqueResult();
	}

	@Override
	public Integer saveAddress(Address address) {
		getSession().persist(address);
        return address.getAdrId();
	}

	@Override
	public Integer updateAddress(Address address) {
		getSession().merge(address);
        return address.getAdrId();
	}

	@Override
	public Boolean deleteAddress(Integer addressId, Integer userId) {
	    
		String hql = "DELETE FROM Address WHERE addressId = :addressId AND userId = :userId";
	    
		int result = getSession().createMutationQuery(hql)
	                    		 .setParameter("addressId", addressId)
	                    		 .setParameter("userId", userId)
	                    		 .executeUpdate();
	    return result > 0;
	}

}
