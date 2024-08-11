package com.example.pets4ever.user.dtos.signInDTO;

import com.example.pets4ever.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


public record SignInResponseDTO(
        @NotNull
        String userId,
        @NotNull
        String username,
        @NotNull
        @Email(message = "O banco de dados forneceu um E-mail inválido!", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        String email,
        @NotNull
        String userProfileImageUrl,
        @NotNull
        String token
) {
    public static SignInResponseDTO fromUserAndToken(User user, String token){
        return new SignInResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getUserProfilePhotoUrl(), token);
    }
}

