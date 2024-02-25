package com.example.pets4ever.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    USER("user");
    private final String role;
    UserRole(String role){
        this.role = role;
    }

}
