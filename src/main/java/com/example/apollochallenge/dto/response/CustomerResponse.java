package com.example.apollochallenge.dto.response;

import com.example.apollochallenge.entity.Tag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
public class CustomerResponse {
    private Integer id;
    private String name;
    @JsonIgnoreProperties("customers")
    private List<Tag> tags;
}
