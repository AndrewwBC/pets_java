package com.example.pets4ever.user.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;

public record UserAuthDTO(
        @Email
        String email,
        @Min(6)
        String password) {
}
