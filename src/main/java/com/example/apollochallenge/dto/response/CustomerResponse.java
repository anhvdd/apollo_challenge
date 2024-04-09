package com.example.apollochallenge.dto.response;

import com.example.apollochallenge.entity.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomerResponse {
    private Integer id;

    private String name;

    private List<TagResponse> tags;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    public static CustomerResponse fromEntity(Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.id = customer.getId();
        customerResponse.name = customer.getName();
        customerResponse.createdTime = customer.getCreatedTime();
        customerResponse.updatedTime = customer.getUpdatedTime();
        List<TagResponse> tagResponses = customerResponse.getTags();
        customer.getTags().forEach(tag -> {
            tagResponses.add(TagResponse.fromEntity(tag));
        });
        customerResponse.tags = tagResponses;
        return customerResponse;
    }
}
