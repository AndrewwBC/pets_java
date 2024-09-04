package com.example.pets4ever.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {

    List<Post> findAllByOrderByCreationDateDesc();
    List<Post> findAllByUserId(String userId);
}
