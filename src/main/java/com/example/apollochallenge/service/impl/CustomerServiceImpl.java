package com.example.apollochallenge.service.impl;

import com.example.apollochallenge.dto.request.CustomerRequest;
import com.example.apollochallenge.dto.response.CustomerResponse;
import com.example.apollochallenge.entity.Customer;
import com.example.apollochallenge.entity.Tag;
import com.example.apollochallenge.exception.ApplicationCustomException;
import com.example.apollochallenge.repository.CustomerRepository;
import com.example.apollochallenge.repository.TagRepository;
import com.example.apollochallenge.service.CustomerService;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final TagRepository tagRepository;

    @Override
    public Page<CustomerResponse> getAll(@Nullable List<String> keys, Pageable pageable) {
        if (keys == null || keys.isEmpty()) {
            return customerRepository.findAllByIsDeleteIs(false, pageable)
                .map(CustomerResponse::fromEntity);
        } else {
            return customerRepository.findCustomersByTagsTitleInOrNameInAndIsDeleteIs(keys, keys, false, pageable)
                .map(CustomerResponse::fromEntity);
        }
    }

    @Override
    public CustomerResponse getCustomer(Integer id) throws Exception {
        Customer customer = customerRepository.findById(id)
            .filter(c -> !c.isDelete())
            .orElseThrow(() -> new Exception("Customer not found"));
        return CustomerResponse.fromEntity(customer);
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        List<Tag> tags = new ArrayList<>();
        request.getTags().forEach(title -> {
            Tag tag = tagRepository.findByTitle(title);
            if( tag == null) {
                Tag newTag = new Tag(title);
                tagRepository.save(newTag);
                tags.add(newTag);
            } else tags.add(tag);
        });
        if (customerRepository.findByName(request.getName()) != null) {
            throw new ApplicationCustomException("Customer already exists");
        }
        customer.setName(request.getName());
        customer.setTags(tags);
        customerRepository.save(customer);
        return CustomerResponse.fromEntity(customer);
    }

    @Override
    public CustomerResponse updateCustomer(Integer id, CustomerRequest request) throws Exception {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new ApplicationCustomException("Customer not found"));
        List<Tag> tags = new ArrayList<>();
        request.getTags().forEach(title -> {
            Tag tag = tagRepository.findByTitle(title);
            if( tag == null) {
                Tag newTag = new Tag(title);
                tagRepository.save(newTag);
                tags.add(newTag);
            } else tags.add(tag);
        });
        if (customerRepository.findByName(request.getName()) != null && !customer.getName().equals(request.getName())) {
            throw new ApplicationCustomException("Customer name already exists");
        }
        customer.setName(request.getName());
        customer.setTags(tags);
        customerRepository.save(customer);
        return CustomerResponse.fromEntity(customer);
    }

    @Override
    public void deleteCustomer(Integer id) throws Exception {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new Exception("Customer not found"));
        customer.setDelete(true);
        customerRepository.save(customer);
    }
}
