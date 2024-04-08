package com.example.apollochallenge.controller;

import com.example.apollochallenge.dto.request.TagRequest;
import com.example.apollochallenge.dto.response.TagResponse;
import com.example.apollochallenge.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/tags")
@AllArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAll () {
        return ResponseEntity.ok().body(tagService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TagResponse> getOne(@PathVariable Integer id) throws Exception {
        return ResponseEntity.status(200).body(tagService.getTag(id));
    }

    @PostMapping
    public ResponseEntity<TagResponse> create(@RequestBody TagRequest request) {
        return ResponseEntity.status(201).body(tagService.createTag(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TagResponse> update(@PathVariable Integer id, @RequestBody TagRequest request) throws Exception {
        return ResponseEntity.status(200).body(tagService.updateTag(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) throws Exception {
        tagService.deleteTag(id);
    }
}
