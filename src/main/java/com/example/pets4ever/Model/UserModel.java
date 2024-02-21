package com.example.pets4ever.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID  id;

    private String name;
    private String password;
    private String role;

    public UserModel(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

}
