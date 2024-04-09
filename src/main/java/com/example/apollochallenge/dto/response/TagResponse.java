package com.example.apollochallenge.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class TagResponse {
    private Integer id;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updatedTime;
}
