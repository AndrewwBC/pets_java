package com.example.pets4ever.user.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PatchPasswordDTO(

        @NotNull(message = "Senha atual deve ser preenchida.")
        @NotEmpty(message = "Senha atual deve ser preenchida.")
        String actualPassword,
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,64}$",
                message = "A senha deve ter de 8 a 64 caracteres, incluir uma letra maiúscula, minúscula e um número."
        )
        String newPassword) {
}
