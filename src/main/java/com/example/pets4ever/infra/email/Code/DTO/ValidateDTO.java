package com.example.pets4ever.infra.email.Code.DTO;

import jakarta.validation.constraints.NotNull;

public record ValidateDTO(
        @NotNull
        String code
)
{}
