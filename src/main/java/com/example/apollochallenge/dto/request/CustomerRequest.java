package com.example.apollochallenge.dto.request;

import com.example.apollochallenge.constant.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CustomerRequest {
    @NotBlank(message = Constants.EMPTY_INPUT_NAME)
    @Size(max = 20, message = Constants.INPUT_NAME_LIMITATION)
    private String name;
    @NotNull
    private List<String> tags;
}
