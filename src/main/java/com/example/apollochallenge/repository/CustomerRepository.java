package com.example.apollochallenge.repository;

import com.example.apollochallenge.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Page<Customer> findCustomersByTagsTitleInOrNameIn(List<String> tags, List<String> name, Pageable pageable);
}
