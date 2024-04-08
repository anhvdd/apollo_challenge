package com.example.apollochallenge.service;

import com.example.apollochallenge.dto.request.CustomerRequest;
import com.example.apollochallenge.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getAll();

    CustomerResponse getCustomer(Integer id) throws Exception;

    List<CustomerResponse> getCustomerByName(String name);
    List<CustomerResponse> getCustomerByTags(List<Integer> tagsID);

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse updateCustomer(Integer id, CustomerRequest request) throws Exception;

    void deleteCustomer(Integer id) throws Exception;

}
