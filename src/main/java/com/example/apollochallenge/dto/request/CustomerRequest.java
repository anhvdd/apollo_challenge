package com.example.apollochallenge.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CustomerRequest {
    @NotBlank(message = "Name must not be empty")
    @Size(max = 20, message = "Name must be less than 20 characters")
    private String name;
    @NotNull
    private List<String> tags;
}
