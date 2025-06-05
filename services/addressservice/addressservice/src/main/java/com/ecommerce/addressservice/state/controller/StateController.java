package com.ecommerce.addressservice.state.controller;

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

import com.ecommerce.addressservice.dto.StateRequestDto;
import com.ecommerce.addressservice.dto.StateResponceDto;
import com.ecommerce.addressservice.state.service.StateService;
import com.ecommerce.addressservice.util.AddressResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("state")
public class StateController {


    private final StateService service;

    @GetMapping("/get-state-list")
    public AddressResponse getStateList() {
        List<StateResponceDto> list = service.getStateList();
        return new AddressResponse(true, HttpStatus.OK, "Successfully fetched data", list);
    }

    @GetMapping("/get-state-by-id/{stateId}")
    public AddressResponse getCountryById(@PathVariable @NotNull @Positive Integer stateId) {
        StateResponceDto state = service.getStateById(stateId); // should throw StateNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "State found", state);
    }

    @PostMapping("/save-state")
    public AddressResponse saveState(@RequestBody @Valid StateRequestDto dto) {
        Integer savedStateId = service.saveState(dto);
        return new AddressResponse(true, HttpStatus.CREATED, "State saved successfully", savedStateId);
    }

    @PutMapping("/update-state/{stateId}")
    public AddressResponse updateState(@PathVariable @NotNull @Positive Integer stateId, @RequestBody @Valid StateRequestDto dto) {
        Integer updatedStateId = service.updateState(stateId, dto);
        return new AddressResponse(true, HttpStatus.OK, "State updated successfully", updatedStateId);
    }

    @DeleteMapping("/delete-state/{stateId}")
    public AddressResponse deleteState(@PathVariable @NotNull @Positive Integer stateId) {
        service.deleteState(stateId); // should throw StateNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "State deleted successfully", null);
    }
}
