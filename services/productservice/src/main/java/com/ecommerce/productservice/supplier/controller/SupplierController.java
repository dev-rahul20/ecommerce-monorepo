package com.ecommerce.productservice.supplier.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productservice.dto.SupplierRequestDto;
import com.ecommerce.productservice.dto.SupplierResponseDto;
import com.ecommerce.productservice.supplier.service.SupplierService;
import com.ecommerce.productservice.util.SupplierResponce;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product-supplier")
public class SupplierController {

	private final SupplierService service;

	@GetMapping("get-all")
	public SupplierResponce getAllSupplier() {
		List<SupplierResponseDto> supplier = service.getAllSupplier();
		return new SupplierResponce(true, HttpStatus.OK, "Successfully fetched data", supplier);
	}

	@GetMapping("get-by-id/{supplierId}")
	public SupplierResponce getSupplierById(@PathVariable @Positive @Valid Integer supplierId) {
		SupplierResponseDto supplier = service.getSupplierById(supplierId);
		return new SupplierResponce(true, HttpStatus.OK, "Successfully fetched data", supplier);
	}

	@PostMapping("save")
	public SupplierResponce saveSupplier(@RequestBody @Valid SupplierRequestDto dto) {
		Integer savedSupplierId = service.saveSupplier(dto);
		return new SupplierResponce(true, HttpStatus.OK, "Supplier saved Successfully", savedSupplierId);
	}

	@PutMapping("update/{supplierId}")
	public SupplierResponce updateSupplier(@PathVariable @Positive @NotNull Integer supplierId,
			@RequestBody @Valid SupplierRequestDto dto) {
		Integer updatedSupplierId = service.updateSupplier(supplierId, dto);
		return new SupplierResponce(true, HttpStatus.OK, "Supplier updated Successfully", updatedSupplierId);
	}

	@DeleteMapping("delete/{supplierId}")
	public SupplierResponce deleteSupplier(@PathVariable @Positive @Valid Integer supplierId) {
		Boolean isDeleted = service.deleteSupplier(supplierId);
		return new SupplierResponce(true, HttpStatus.OK, "Supplier deleted Successfully", isDeleted);
	}

}
