package com.example.pets4ever.post;

import com.example.pets4ever.storie.Storie;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {

    List<Post> findAllByOrderByCreationDateDesc();
    List<Post> findAllByUserId(String userId);


    @Query("SELECT s FROM Storie s")
    List<Storie> findAllStories();;

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Posts WHERE id = :id")
    void deleteById(@NotNull String id);
}
