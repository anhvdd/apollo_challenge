package com.example.apollochallenge.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class CustomerResponse {
    private String id;

    private String name;

    private List<TagResponse> tags;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updatedTime;

}
