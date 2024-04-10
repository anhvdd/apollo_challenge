package com.example.apollochallenge.service;

import com.example.apollochallenge.dto.request.CustomerRequest;
import com.example.apollochallenge.dto.response.CustomerResponse;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    Page<CustomerResponse> getAll(@Nullable List<String> keys, Pageable pageable);

    CustomerResponse getCustomerById(Integer id);

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse updateCustomer(Integer id, CustomerRequest request) throws Exception;

    Boolean deleteCustomer(Integer id) throws Exception;

}
