package com.example.pets4ever.user.DTO.UpdateDTO;

import com.example.pets4ever.user.DTO.Register.Validator.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdateDTO(
        @NotBlank
        @Length(min = 3, max = 32, message = "O nome deve ter de 3 a 32 caractéres!")
        String name,
        @NotBlank
        @Email(message = "Email inválido!", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        String email,
        @NotBlank
        @Length(min = 3, max = 64, message = "A senha deve ter de 8 a 64 caractéres!")
        String password) {
}
