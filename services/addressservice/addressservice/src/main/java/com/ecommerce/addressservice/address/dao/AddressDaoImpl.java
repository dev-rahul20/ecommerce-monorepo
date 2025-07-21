package com.ecommerce.addressservice.address.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.addressservice.dto.AddressResponseDto;
import com.ecommerce.addressservice.entity.Address;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class AddressDaoImpl implements AddressDao {
	
    private final SessionFactory sessionFactory;
	
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
	
	@Override
	public List<AddressResponseDto> getAllAddress() {
		return getSession().createQuery("FROM Address", AddressResponseDto.class).getResultList();
	}

	@Override
	public Address getByAddressId(Integer addressId) {
		 return getSession().get(Address.class, addressId);
	}
	
	@Override
	public List<AddressResponseDto> getByUserId(Integer userId) {
		
		return getSession().createQuery("FROM Address WHERE userId = :userId", AddressResponseDto.class)
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
	public Boolean deleteAddress(Address address) {
		getSession().remove(address);
	    return true;
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
