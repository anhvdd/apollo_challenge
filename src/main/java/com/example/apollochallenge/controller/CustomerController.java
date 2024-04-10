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

    /**
     * Get all customers with optional keys and pagination.
     *
     * @param  keys      list of keys for filtering
     * @param  pageable  pagination information
     * @return           response entity with customer page
     */
    @GetMapping
    public ResponseEntity<BaseResponse<Page<CustomerResponse>>> getAll(@RequestParam(value = "keys", required = false) List<String> keys, Pageable pageable) {
        BaseResponse<Page<CustomerResponse>> response = BaseResponse.toSuccess(customerService.getAll(keys, pageable));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Get one customer by ID.
     *
     * @param  id  the ID of the customer
     * @return     the response entity containing the customer response
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BaseResponse<CustomerResponse>> getOne(@PathVariable Integer id) {
        BaseResponse<CustomerResponse> response = BaseResponse.toSuccess(customerService.getCustomerById(id));
        return ResponseEntity.ok().body(response);
    }

    /**
     * Create a new customer based on the provided request.
     *
     * @param  request   the customer request object to create
     * @return           the response entity with the created customer details
     */
    @PostMapping
    public ResponseEntity<BaseResponse<CustomerResponse>> create(@RequestBody @Valid CustomerRequest request) {
        BaseResponse<CustomerResponse> response = BaseResponse.toSuccess(customerService.createCustomer(request));
        return ResponseEntity.ok().body(response);
    }

    /**
     * Update a customer with the provided ID using the given request data.
     *
     * @param  id      the ID of the customer to update
     * @param  request the data to update the customer with
     * @return         the response entity with the updated customer information
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BaseResponse<CustomerResponse>> update(@PathVariable Integer id, @RequestBody @Valid CustomerRequest request) throws Exception {
        BaseResponse<CustomerResponse> response = BaseResponse.toSuccess(customerService.updateCustomer(id, request));
        return ResponseEntity.ok().body(response);
    }

    /**
     * Deletes a customer by ID.
     *
     * @param  id	the ID of the resource to delete
     * @return      a response entity with a base response containing a boolean indicating success
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BaseResponse<Boolean>> delete(@PathVariable Integer id) throws Exception {
        BaseResponse<Boolean> response = BaseResponse.toSuccess(customerService.deleteCustomer(id));
        return ResponseEntity.ok().body(response);
    }
}
