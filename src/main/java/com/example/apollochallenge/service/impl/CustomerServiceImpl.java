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


    /**
     * Get all customers with the specified keys and pageable information.
     *
     * @param  keys      a list of keys to filter the customers
     * @param  pageable  pagination information for retrieving customers
     * @return           a page of customer responses
     */
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

    /**
     * Retrieves a customer by their ID.
     *
     * @param  id    the ID of the customer to retrieve
     * @return       a response containing the customer details
     */
    @Override
    public CustomerResponse getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
            .filter(c -> !c.isDelete())
            .orElseThrow(() -> new ApplicationCustomException(CUSTOMER_NOT_FOUND));
        return CustomerResponse.fromEntity(customer);
    }

    /**
     * Creates a new customer based on the provided request.
     *
     * @param  request   the customer request object
     * @return           the response containing the newly created customer
     */
    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = handleCustomerRequest(null, request);
        if (customerRepository.findByName(request.getName()) != null) {
            throw new ApplicationCustomException(CUSTOMER_EXISTS);
        }
        customerRepository.save(customer);
        return CustomerResponse.fromEntity(customer);
    }

    /**
     * Updates a customer based on the provided id and request.
     *
     * @param  id       the id of the customer to be updated
     * @param  request  the request containing the updated customer information
     * @return          the response containing the updated customer information
     */
    @Override
    public CustomerResponse updateCustomer(Integer id, CustomerRequest request) {
        Customer customer = handleCustomerRequest(id, request);
        if (customerRepository.findByName(request.getName()) != null && !customer.getName().equals(request.getName())) {
            throw new ApplicationCustomException(CUSTOMER_EXISTS);
        }
        customerRepository.save(customer);
        return CustomerResponse.fromEntity(customer);
    }

    /**
     * Deletes a customer by setting the delete flag to true in the database.
     *
     * @param  id  the ID of the customer to delete
     * @return     true if the customer is successfully deleted, false otherwise
     */
    @Override
    public Boolean deleteCustomer(Integer id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new ApplicationCustomException(CUSTOMER_NOT_FOUND));
        customer.setDelete(true);
        Customer result = customerRepository.save(customer);
        return result.isDelete();
    }

    /**
     * Handles a customer request by processing the given ID and request data.
     *
     * @param  id         the ID of the customer (nullable)
     * @param  request    the customer request data
     * @return            the updated customer object
     */
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
