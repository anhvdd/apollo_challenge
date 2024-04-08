package com.example.apollochallenge.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TagRequest {
    @NotBlank(message = "Customer's name cannot be empty" )
    @Length(max = 20, message = "Customer's name must be less than 20 characters")
    private String title;
}
