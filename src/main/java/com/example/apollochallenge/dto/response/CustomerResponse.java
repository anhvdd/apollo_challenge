package com.example.apollochallenge.dto.response;

import com.example.apollochallenge.entity.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.apollochallenge.constant.Constants.DATE_TIME_FORMAT;

@Data
public class CustomerResponse {
    private Integer id;

    private String name;

    private List<TagResponse> tags;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdTime;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime updatedTime;

    public static CustomerResponse fromEntity(Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.id = customer.getId();
        customerResponse.name = customer.getName();
        customerResponse.createdTime = customer.getCreatedTime();
        customerResponse.updatedTime = customer.getUpdatedTime();
        List<TagResponse> tagResponses = new ArrayList<>();
        customer.getTags().forEach(tag -> tagResponses.add(TagResponse.fromEntity(tag)));
        customerResponse.tags = tagResponses;
        return customerResponse;
    }
}
