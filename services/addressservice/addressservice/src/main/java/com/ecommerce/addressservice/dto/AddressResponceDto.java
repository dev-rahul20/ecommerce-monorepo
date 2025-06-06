package com.ecommerce.addressservice.dto;


import com.ecommerce.addressservice.entity.City;
import com.ecommerce.addressservice.entity.Country;
import com.ecommerce.addressservice.entity.State;


public class AddressResponceDto {

    private Integer adrId;
    private Integer userId;
    private String adrStreet;
    private String adrArea;
    private String adrLandmark;
    private Integer adrPinno;
    private City city;
    private State state;
    private Country country;
    private String adrType;
	
}
