package com.example.pets4ever.user.dtos;

public record ForgotPasswordDTO  (
        String code,
        String username,
        String newPassword
) {}
