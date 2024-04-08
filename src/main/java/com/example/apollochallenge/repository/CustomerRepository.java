package com.example.apollochallenge.repository;

import com.example.apollochallenge.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findCustomerByName(String name);
    List<Customer> findCustomersByTagsIn(List<Integer> tags);
}
