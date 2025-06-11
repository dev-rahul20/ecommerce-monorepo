
package com.ecommerce.productservice.supplier.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.dto.SupplierRequestDto;
import com.ecommerce.productservice.dto.SupplierResponseDto;
import com.ecommerce.productservice.entity.Supplier;
import com.ecommerce.productservice.exception.SupplierNotFoundException;
import com.ecommerce.productservice.exception.SupplierNotSaveException;
import com.ecommerce.productservice.exception.SupplierNotUpdateException;
import com.ecommerce.productservice.supplier.dao.SupplierDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SupplierServiceImpl implements SupplierService {
	
	private final SupplierDao dao;
	private final ModelMapper modelMapper;

	private Supplier checkSupplierExistOrNot(Integer supplierId) {

		return Optional.ofNullable(dao.getSupplierBySupplierId(supplierId))
					   .orElseThrow(() -> new SupplierNotFoundException("Supplier with id: " + supplierId + " not found"));
	}

	@Override
	public List<SupplierResponseDto> getAllSupplier() {

		List<SupplierResponseDto> list = dao.getAllSupplier();

		return list == null ? Collections.emptyList() : list;
	}

	@Override
	public SupplierResponseDto getSupplierById(Integer supplierId) {
		
		Supplier supplier = checkSupplierExistOrNot(supplierId);

		return modelMapper.map(supplier, SupplierResponseDto.class);
	}

	@Override
	public Integer saveSupplier(SupplierRequestDto dto) {
		
		Supplier supplier = modelMapper.map(dto, Supplier.class);
		
		Integer savedSupplierId = dao.saveSupplier(supplier);
		
		return Optional.ofNullable(savedSupplierId)
					   .filter(id -> id > 0)
					   .orElseThrow(() -> new SupplierNotSaveException("Supplier not saved"));
	}

	@Override
	public Integer updateSupplier(Integer supplierId, SupplierRequestDto dto) {
		
		checkSupplierExistOrNot(supplierId); 

	    Supplier supplier = modelMapper.map(dto, Supplier.class);
	    supplier.setId(supplierId); 

	    Integer updatedSupplierId = dao.updateSupplier(supplier);

	    return Optional.ofNullable(updatedSupplierId)
	                   .filter(id -> id > 0)
	                   .orElseThrow(() -> new SupplierNotUpdateException("Supplier not updated"));
	}

	@Override
	public Boolean deleteSupplier(Integer supplierId) {
		 
		 Supplier supplier = checkSupplierExistOrNot(supplierId);
			
		 return dao.deleteSupplierBySupplierId(supplier);
	}

	


}
