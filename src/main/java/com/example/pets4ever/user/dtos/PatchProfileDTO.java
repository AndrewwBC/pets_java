package com.example.pets4ever.user.dtos;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record PatchProfileDTO(
        @Length(min = 1, message = "Nome completo deve ter no mínimo 1 caractér")
        @NotNull(message = "Nome completo é obrigatório.")
        String fullname,
        @Length(min = 3, max = 32, message = "Nome de usuário deve ter de 3 a 128 caractéres")
        @NotNull(message = "Nome de usuário é obrigatório.")
        String username,
        @Length(max = 80, message = "Apenas 80 caractéres são permitidos")
        @NotNull(message = "Nome de usuário é obrigatório.")
        String bio) {}
