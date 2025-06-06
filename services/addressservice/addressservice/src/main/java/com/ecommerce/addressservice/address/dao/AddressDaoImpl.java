package com.ecommerce.addressservice.address.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecommerce.addressservice.dto.AddressResponceDto;
import com.ecommerce.addressservice.entity.Address;

@Repository
public class AddressDaoImpl implements AddressDao {
	
	@Autowired
    private SessionFactory sessionFactory;
	
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
	
	@Override
	public List<AddressResponceDto> getAllAddress() {
		return getSession().createQuery("FROM Address", AddressResponceDto.class).getResultList();
	}

	@Override
	public AddressResponceDto getByAddressId(Integer addressId) {
		
		 return getSession().createQuery("FROM Address WHERE adrId = :addressId", AddressResponceDto.class)
                 			.setParameter("addressId", addressId)
                 			.uniqueResult();
	}
	
	@Override
	public List<AddressResponceDto> getByUserId(Integer userId) {
		
		return getSession().createQuery("FROM Address WHERE userId = :userId", AddressResponceDto.class)
						   .setParameter("userId", userId)
						   .getResultList();
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
	public Boolean deleteAddress(Integer addressId) {
	    
		String hql = "DELETE FROM Address WHERE adrId = :addressId";
	    
		int result = getSession().createMutationQuery(hql)
	                    		 .setParameter("addressId", addressId)
	                    		 .executeUpdate();
	    return result > 0;
	}

	@Override
	public Boolean deleteAddressByUserId(Integer userId) {
		
		String hql = "DELETE FROM Address WHERE userId = :userId";
	    
		int result = getSession().createMutationQuery(hql)
	                    		 .setParameter("userId", userId)
	                    		 .executeUpdate();
	    return result > 0;
		
	}

}
