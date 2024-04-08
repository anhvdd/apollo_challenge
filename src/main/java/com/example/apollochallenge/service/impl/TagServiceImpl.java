package com.example.apollochallenge.service.impl;

import com.example.apollochallenge.dto.request.TagRequest;
import com.example.apollochallenge.dto.response.TagResponse;
import com.example.apollochallenge.entity.Tag;
import com.example.apollochallenge.repository.TagRepository;
import com.example.apollochallenge.service.TagService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<TagResponse> getAll() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
            .filter(tag -> !tag.isDelete())
            .map(tag -> modelMapper.map(tag, TagResponse.class))
            .collect(Collectors.toList());
    }

    @Override
    public TagResponse getTag(Integer id) throws Exception {
        Tag tag = tagRepository.findById(id)
            .orElseThrow(() -> new Exception("Tag not found"));
        return modelMapper.map(tag, TagResponse.class);
    }

    @Override
    public TagResponse createTag(TagRequest request) {
        if (tagRepository.findByTitle(request.getTitle()) != null) {
            throw new RuntimeException("Tag already exists");
        }
        Tag tag = modelMapper.map(request, Tag.class);
        tagRepository.save(tag);
        return modelMapper.map(tag, TagResponse.class);
    }

    @Override
    public TagResponse updateTag(Integer id, TagRequest request) throws Exception {
        Tag tag = tagRepository.findById(id)
            .orElseThrow(() -> new Exception("Tag not found"));
        tag.setTitle(request.getTitle());

        tagRepository.save(tag);
        return modelMapper.map(tag, TagResponse.class);
    }

    @Override
    public void deleteTag(Integer id) throws Exception {
        Tag tag = tagRepository.findById(id)
            .orElseThrow(() -> new Exception("Tag not found"));
        tag.setDelete(true);
        tagRepository.save(tag);
    }
}
