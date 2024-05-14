package com.example.pets4ever.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByName(String name);
    UserDetails findByEmail(String email);

    @Query(value = "SELECT email FROM Users WHERE email = :email")
    String findByEmailToCheckLogin(String email);
}
