package com.ecommerce.productservice.supplier.dao;

import java.util.List;

import com.ecommerce.productservice.dto.SupplierResponseDto;
import com.ecommerce.productservice.entity.Supplier;

public interface SupplierDao {
	
	List<SupplierResponseDto> getAllSupplier();

	Supplier getSupplierBySupplierId(Integer supplierId);

	Integer saveSupplier(Supplier supplier);

	Integer updateSupplier(Supplier supplier);

	Boolean deleteSupplierBySupplierId(Supplier supplier);

}
