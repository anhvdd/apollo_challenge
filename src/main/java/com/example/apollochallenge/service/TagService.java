package com.example.apollochallenge.service;

import com.example.apollochallenge.dto.request.TagRequest;
import com.example.apollochallenge.dto.response.TagResponse;

import java.util.List;

public interface TagService {
    List<TagResponse> getAll();

    TagResponse getTag(Integer id) throws Exception;

    TagResponse createTag(TagRequest request);

    TagResponse updateTag(Integer id, TagRequest request) throws Exception;

    void deleteTag(Integer id) throws Exception;
}
