package com.example.pets4ever.user.dtos.signUpDTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class signUpDTO {

        @Length(min = 1, max = 64, message = "Nome deve ter de 1 a 64 caractéres")
        @NotNull(message = "Preencha o nome.")
        String fullname;
        @Length(min = 3, max = 32, message = "Nome de usuário deve ter de 3 a 32 caractéres")
        @NotNull(message = "Preencha o nome de usuário.")
        String username;
        @Email(message = "Email inválido!", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        String email;
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,64}$",
                message = "A senha deve ter de 8 a 64 caracteres, incluir uma letra maiúscula, minúscula e um número."
        )
        String password;

}
