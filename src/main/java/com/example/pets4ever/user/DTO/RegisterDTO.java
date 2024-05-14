package com.example.pets4ever.user.DTO;

import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String password) {

}
