package com.example.apollochallenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag extends BaseEntity {
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ManyToMany(mappedBy = "tags")
    private List<Customer> customers;

    public Tag(String title) {
        this.title = title;
    }
}
