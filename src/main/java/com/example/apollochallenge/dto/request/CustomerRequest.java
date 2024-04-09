package com.example.apollochallenge.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CustomerRequest {
    @NotBlank
    private String name;
    private List<String> tags;
}
