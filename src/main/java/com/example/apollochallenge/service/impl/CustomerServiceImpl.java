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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.apollochallenge.constant.Constants.CUSTOMER_EXISTS;
import static com.example.apollochallenge.constant.Constants.CUSTOMER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
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
    public CustomerResponse getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
            .filter(c -> !c.isDelete())
            .orElseThrow(() -> new ApplicationCustomException(CUSTOMER_NOT_FOUND));
        return CustomerResponse.fromEntity(customer);
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = handleCustomerRequest(null, request);
        if (customerRepository.findByName(request.getName()) != null) {
            throw new ApplicationCustomException(CUSTOMER_EXISTS);
        }
        customerRepository.save(customer);
        return CustomerResponse.fromEntity(customer);
    }

    @Override
    public CustomerResponse updateCustomer(Integer id, CustomerRequest request) {
        Customer customer = handleCustomerRequest(id, request);
        if (customerRepository.findByName(request.getName()) != null && !customer.getName().equals(request.getName())) {
            throw new ApplicationCustomException(CUSTOMER_EXISTS);
        }
        customerRepository.save(customer);
        return CustomerResponse.fromEntity(customer);
    }

    @Override
    public Boolean deleteCustomer(Integer id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new ApplicationCustomException(CUSTOMER_NOT_FOUND));
        customer.setDelete(true);
        Customer result = customerRepository.save(customer);
        return result.isDelete();
    }

    private Customer handleCustomerRequest(@Nullable Integer id, CustomerRequest request) {
        Customer customer = new Customer();
        if (id != null) {
            customer = customerRepository.findById(id)
                .orElseThrow(() -> new ApplicationCustomException(CUSTOMER_NOT_FOUND));
        }
        List<Tag> tags = tagRepository.findTagByTitleIn(request.getTags());
        List<String> tagsTitle = tags.stream().map(Tag::getTitle).toList();
        List<Tag> tagsNotExists = request.getTags().stream()
            .filter(title -> !tagsTitle.contains(title))
            .map(Tag::new).toList();
        tags.addAll(tagsNotExists);

        customer.setTags(tags);
        customer.setName(request.getName());
        return customer;
    }
}
