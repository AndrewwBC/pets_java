package com.example.pets4ever.user.DTO.SignIn;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public record SignInDTO(

        @NotNull(message = "Preencha o Email!")
        @Email(message = "Email inválido!", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        String email,

        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,64}$",
                message = "Senha incorreta!"
        )
        String password) {
}
