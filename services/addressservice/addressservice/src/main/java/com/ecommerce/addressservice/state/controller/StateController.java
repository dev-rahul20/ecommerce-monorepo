package com.ecommerce.addressservice.state.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.addressservice.entity.State;
import com.ecommerce.addressservice.state.service.StateService;
import com.ecommerce.addressservice.util.AddressResponse;



@RestController
@RequestMapping("state")
public class StateController {

    @Autowired
    private StateService service;

    @GetMapping("/get-state-list")
    public AddressResponse getStateList() {
        List<State> list = service.getStateList();
        return new AddressResponse(true, HttpStatus.OK, "Successfully fetched data", list);
    }

    @GetMapping("/get-state-by-id/{stateId}")
    public AddressResponse getCountryById(@PathVariable Integer stateId) {
        State state = service.getStateById(stateId); // should throw StateNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "State found", state);
    }

    @PostMapping("/save-state")
    public AddressResponse saveState(@RequestBody State state) {
        Integer savedStateId = service.saveState(state);
        return new AddressResponse(true, HttpStatus.CREATED, "State saved successfully", savedStateId);
    }

    @PostMapping("/update-state")
    public AddressResponse updateState(@RequestBody State state) {
        Integer updatedStateId = service.updateState(state);
        return new AddressResponse(true, HttpStatus.OK, "State updated successfully", updatedStateId);
    }

    @DeleteMapping("/delete-state/{stateId}")
    public AddressResponse deleteState(@PathVariable Integer stateId) {
        service.deleteState(stateId); // should throw StateNotFoundException if not found
        return new AddressResponse(true, HttpStatus.OK, "State deleted successfully", null);
    }
}
