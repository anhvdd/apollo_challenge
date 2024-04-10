package com.example.apollochallenge.controller;

import com.example.apollochallenge.dto.request.CustomerRequest;
import com.example.apollochallenge.dto.response.BaseResponse;
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

import static com.example.apollochallenge.constant.Constants.BASE_URL_CUSTOMERS;

@RestController
@RequestMapping(value = BASE_URL_CUSTOMERS)
@AllArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<BaseResponse<Page<CustomerResponse>>> getAll(@RequestParam(value = "keys", required = false) List<String> keys, Pageable pageable) {
        BaseResponse<Page<CustomerResponse>> response = BaseResponse.toSuccess(customerService.getAll(keys, pageable));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BaseResponse<CustomerResponse>> getOne(@PathVariable Integer id) {
        BaseResponse<CustomerResponse> response = BaseResponse.toSuccess(customerService.getCustomerById(id));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CustomerResponse>> create(@RequestBody @Valid CustomerRequest request) {
        BaseResponse<CustomerResponse> response = BaseResponse.toSuccess(customerService.createCustomer(request));
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BaseResponse<CustomerResponse>> update(@PathVariable Integer id, @RequestBody @Valid CustomerRequest request) throws Exception {
        BaseResponse<CustomerResponse> response = BaseResponse.toSuccess(customerService.updateCustomer(id, request));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BaseResponse<Boolean>> delete(@PathVariable Integer id) throws Exception {
        BaseResponse<Boolean> response = BaseResponse.toSuccess(customerService.deleteCustomer(id));
        return ResponseEntity.ok().body(response);
    }
}
