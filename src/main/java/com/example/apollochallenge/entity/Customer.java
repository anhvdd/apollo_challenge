package com.example.apollochallenge.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "customer")
@Data
public class Customer extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "customer_tag",
            joinColumns = { @JoinColumn(name = "customer_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    private List<Tag> tags;
}
