package com.example.apollochallenge.service.impl;

import com.example.apollochallenge.dto.request.CustomerRequest;
import com.example.apollochallenge.dto.response.CustomerResponse;
import com.example.apollochallenge.entity.Customer;
import com.example.apollochallenge.entity.Tag;
import com.example.apollochallenge.repository.CustomerRepository;
import com.example.apollochallenge.repository.TagRepository;
import com.example.apollochallenge.service.CustomerService;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
            Page<Customer> customersPage = customerRepository.findAll(pageable);
            List<CustomerResponse> response = customersPage.getContent().stream()
                .filter(customer -> !customer.isDelete())
                .map(CustomerResponse::fromEntity)
                .toList();
            return new PageImpl<>(response, pageable, customersPage.getTotalElements());
        }
        Page<Customer> customers = customerRepository.findCustomersByTagsTitleInOrNameIn(keys, keys, pageable);
        List<CustomerResponse> response = customers
            .stream()
            .map(CustomerResponse::fromEntity)
            .toList();
        return new PageImpl<>(response, pageable, response.size());
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
                tagRepository.save(new Tag(title));
            }
             tags.add(tag);
        });
        customer.setName(request.getName());
        customer.setTags(tags);
        customerRepository.save(customer);
        return CustomerResponse.fromEntity(customer);
    }

    @Override
    public CustomerResponse updateCustomer(Integer id, CustomerRequest request) throws Exception {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new Exception("Customer not found"));
        List<Tag> tags = tagRepository.findTagByTitleIn(request.getTags());
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
