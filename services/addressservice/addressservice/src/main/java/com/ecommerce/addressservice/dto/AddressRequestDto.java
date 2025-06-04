package com.ecommerce.addressservice.dto;



import com.ecommerce.addressservice.validation.OnCreateValidation;
import com.ecommerce.addressservice.validation.OnUpdateValidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddressRequestDto {

	@NotNull(groups = {OnUpdateValidation.class})
    private Integer adrId;

    @NotBlank(groups = {OnCreateValidation.class, OnUpdateValidation.class})
    private String adrStreet;

    @NotBlank(groups = {OnCreateValidation.class, OnUpdateValidation.class})
    private String adrArea;

    private String adrLandmark;

    @NotBlank(groups = {OnCreateValidation.class, OnUpdateValidation.class})
    private String adrType;
    
    @NotNull
    @Positive
    private Integer adrPinno;

    @NotNull
    @Positive
    private Integer cityId;

    @NotNull
    @Positive
    private Integer stateId;

    @NotNull
    @Positive
    private Integer countryId;


    
    public Integer getAdrId() {
        return adrId;
    }

    public void setAdrId(Integer adrId) {
        this.adrId = adrId;
    }

    public String getAdrStreet() {
        return adrStreet;
    }

    public void setAdrStreet(String adrStreet) {
        this.adrStreet = adrStreet;
    }

    public String getAdrArea() {
        return adrArea;
    }

    public void setAdrArea(String adrArea) {
        this.adrArea = adrArea;
    }

    public String getAdrLandmark() {
        return adrLandmark;
    }

    public void setAdrLandmark(String adrLandmark) {
        this.adrLandmark = adrLandmark;
    }

    public Integer getAdrPinno() {
        return adrPinno;
    }

    public void setAdrPinno(Integer adrPinno) {
        this.adrPinno = adrPinno;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getAdrType() {
        return adrType;
    }

    public void setAdrType(String adrType) {
        this.adrType = adrType;
    }
}
