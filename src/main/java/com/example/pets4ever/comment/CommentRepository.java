package com.example.pets4ever.comment;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {

    List<Comment> findByPostId(String postId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Comment WHERE id = :id")
    void deleteById(@NotNull String id);

}
