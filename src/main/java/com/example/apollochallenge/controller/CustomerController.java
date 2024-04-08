package com.example.apollochallenge.controller;

import com.example.apollochallenge.dto.request.CustomerRequest;
import com.example.apollochallenge.dto.response.CustomerResponse;
import com.example.apollochallenge.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAll() {
        return ResponseEntity.ok().body(customerService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerResponse> getOne(@PathVariable Integer id) throws Exception {
        return ResponseEntity.status(200).body(customerService.getCustomer(id));
    }

    @GetMapping(value = "/findByName")
    public ResponseEntity<List<CustomerResponse>> findByName(@RequestParam(value = "name") String name) {
        return ResponseEntity.status(200).body(customerService.getCustomerByName(name));
    }

    @GetMapping(value = "/findByTag")
    public ResponseEntity<List<CustomerResponse>> findByTag(@RequestParam(value = "tagsID") List<Integer> tagsID) {
        return ResponseEntity.status(200).body(customerService.getCustomerByTags(tagsID));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.status(201).body(customerService.createCustomer(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Integer id, @RequestBody @Valid CustomerRequest request) throws Exception {
        return ResponseEntity.status(200).body(customerService.updateCustomer(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) throws Exception {
        customerService.deleteCustomer(id);
    }
}
