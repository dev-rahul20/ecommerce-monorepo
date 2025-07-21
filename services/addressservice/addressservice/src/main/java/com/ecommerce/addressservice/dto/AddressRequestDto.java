package com.ecommerce.addressservice.dto;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonDeserialize
public class AddressRequestDto {

    private Integer adrId;

    @NotNull
    private Integer userId;
    
    @NotBlank
    private String adrStreet;

    @NotBlank
    private String adrArea;

    private String adrLandmark;

    @NotBlank
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

}
