package com.example.pets4ever.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    User findByName(String name);
    User findByEmail(String email);
    @Query(value = "SELECT u FROM Users u WHERE u.email = :email")
    UserDetails findByEmailToReturnUserDetails(String email);
}
