package com.example.apollochallenge.repository;

import com.example.apollochallenge.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByTitle(String title);

    List<Tag> findTagByTitleIn(List<String> titles);
}
