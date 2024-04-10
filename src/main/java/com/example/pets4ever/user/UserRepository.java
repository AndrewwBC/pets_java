package com.example.pets4ever.user;

import com.example.pets4ever.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByName(String name);
    UserDetails findByEmail(String email);
}
