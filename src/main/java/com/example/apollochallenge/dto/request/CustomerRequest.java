package com.example.apollochallenge.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CustomerRequest {
    @NotBlank
    private String name;
    @NotNull
    private List<String> tags;
}
