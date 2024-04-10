package com.example.apollochallenge.controller;

import com.example.apollochallenge.dto.request.CustomerRequest;
import com.example.apollochallenge.dto.response.CustomerResponse;
import com.example.apollochallenge.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAll(@RequestParam(value = "keys", required = false) List<String> keys, Pageable pageable) {
        return ResponseEntity.ok().body(customerService.getAll(keys, pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerResponse> getOne(@PathVariable Integer id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomer(id));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Integer id, @RequestBody @Valid CustomerRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) throws Exception {
        customerService.deleteCustomer(id);
    }
}
