package com.example.pets4ever.user;

import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.DTO.ProfileDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByName(String name);
    User findByEmail(String email);
    @Query(value = "SELECT u FROM Users u WHERE u.email = :email")
    UserDetails findByEmailToReturnUserDetails(String email);

    @Query(value = "SELECT p FROM Posts p WHERE p.user.id = :userId")
    List<Post> getPostsByUserId(String userId);
}
