package com.example.pets4ever.infra.email.Code.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public record ValidateDTO(
        @NotNull
        String code,

        @NotNull
        @Email(message = "Email inválido.")
        String email,
        @NotNull
        @UUID
        String userId
)
{}
