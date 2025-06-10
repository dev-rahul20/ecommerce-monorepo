package com.ecommerce.productservice.supplier.service;

import java.util.List;

import com.ecommerce.productservice.dto.SupplierResponseDto;

public interface SupplierService {
	
	List<SupplierResponseDto> getAllSupplier();

	SupplierResponseDto getSupplierById(Integer supplierId);

	Integer saveSupplier(SupplierResponseDto dto);

	Integer updateSupplier(Integer supplierId, SupplierResponseDto dto);

	Boolean deleteSupplier(Integer supplierId);

}
