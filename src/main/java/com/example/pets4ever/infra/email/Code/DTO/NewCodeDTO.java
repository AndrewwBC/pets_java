package com.example.pets4ever.infra.email.Code.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record NewCodeDTO (

        @Email
        @NotNull
        String email
)
{}
