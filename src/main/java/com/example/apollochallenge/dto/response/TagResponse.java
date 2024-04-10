package com.example.apollochallenge.dto.response;

import com.example.apollochallenge.entity.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

import static com.example.apollochallenge.constant.Constants.DATE_TIME_FORMAT;

@Data
public class TagResponse {
    private Integer id;

    private String title;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdTime;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime updatedTime;

    public static TagResponse fromEntity(Tag tag) {
        TagResponse tagResponse = new TagResponse();
        tagResponse.setId(tag.getId());
        tagResponse.setTitle(tag.getTitle());
        tagResponse.setCreatedTime(tag.getCreatedTime());
        tagResponse.setUpdatedTime(tag.getUpdatedTime());
        return tagResponse;
    }
}
