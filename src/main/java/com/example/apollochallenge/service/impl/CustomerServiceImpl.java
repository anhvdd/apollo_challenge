package com.example.apollochallenge.service.impl;

import com.example.apollochallenge.dto.request.CustomerRequest;
import com.example.apollochallenge.dto.response.CustomerResponse;
import com.example.apollochallenge.entity.Customer;
import com.example.apollochallenge.entity.Tag;
import com.example.apollochallenge.repository.CustomerRepository;
import com.example.apollochallenge.repository.TagRepository;
import com.example.apollochallenge.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CustomerResponse> getAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
            .filter(customer -> !customer.isDelete())
            .map(customer -> modelMapper.map(customer, CustomerResponse.class))
            .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getCustomer(Integer id) throws Exception {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new Exception("Customer not found"));
        return modelMapper.map(customer, CustomerResponse.class);
    }

    @Override
    public List<CustomerResponse> getCustomerByName(String name) {
        List<Customer> customers = customerRepository.findCustomerByName(name);
        return customers
            .stream()
            .map(customer -> modelMapper.map(customer, CustomerResponse.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponse> getCustomerByTags(List<Integer> tagsID) {
        List<Tag> tags = tagRepository.findAllById(tagsID);
        tags.forEach(System.out::println);
//        List<Customer> customers = customerRepository.findCustomerByTags(tags);
        return null;
//            customers
//            .stream()
//            .map(customer -> modelMapper.map(customer, CustomerResponse.class))
//            .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        List<Tag> tags = tagRepository.findAllById(request.getTagsID());
        customer.setName(request.getName());
        customer.setTags(tags);

        customerRepository.save(customer);
        return modelMapper.map(customer, CustomerResponse.class);
    }

    @Override
    public CustomerResponse updateCustomer(Integer id, CustomerRequest request) throws Exception {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new Exception("Customer not found"));
        List<Tag> tags = tagRepository.findAllById(request.getTagsID());
        customer.setName(request.getName());
        customer.setTags(tags);

        customerRepository.save(customer);
        return modelMapper.map(customer, CustomerResponse.class);
    }

    @Override
    public void deleteCustomer(Integer id) throws Exception {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new Exception("Customer not found"));
        customer.setDelete(true);
        customerRepository.save(customer);
    }
}
