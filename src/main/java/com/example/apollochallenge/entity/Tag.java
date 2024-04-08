package com.example.apollochallenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tag")
@Data
public class Tag extends BaseEntity {
    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany(mappedBy = "tags")
    private List<Customer> customers;
}
