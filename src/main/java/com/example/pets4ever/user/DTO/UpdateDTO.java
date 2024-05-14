package com.example.pets4ever.user.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateDTO(
        @NotBlank
        @Min(1)
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Min(6)
        String password) {
}
