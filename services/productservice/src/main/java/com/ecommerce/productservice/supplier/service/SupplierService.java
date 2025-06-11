package com.ecommerce.productservice.supplier.service;

import java.util.List;

import com.ecommerce.productservice.dto.SupplierRequestDto;
import com.ecommerce.productservice.dto.SupplierResponseDto;

public interface SupplierService {
	
	List<SupplierResponseDto> getAllSupplier();

	SupplierResponseDto getSupplierById(Integer supplierId);

	Integer saveSupplier(SupplierRequestDto dto);

	Integer updateSupplier(Integer supplierId, SupplierRequestDto dto);

	Boolean deleteSupplier(Integer supplierId);

}
