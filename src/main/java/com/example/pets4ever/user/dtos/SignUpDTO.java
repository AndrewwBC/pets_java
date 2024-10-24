package com.example.pets4ever.user.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class SignUpDTO {

        @Length(min = 1, message = "Nome completo deve ter no mínimo 1 caractér")
        @NotNull(message = "Nome completo é obrigatório.")
        String fullname;
        @Length(min = 3, max = 32, message = "Nome de usuário deve ter de 3 a 128 caractéres")
        @NotNull(message = "Nome de usuário é obrigatório.")
        String username;
        @Email(message = "Email inválido!", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        String email;
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@#$%^&+=!]{8,64}$",
                message = "A senha deve ter de 8 a 64 caracteres, incluir uma letra maiúscula, minúscula e um número. Caracteres especiais são opcionais."
        )
        String password;


}
