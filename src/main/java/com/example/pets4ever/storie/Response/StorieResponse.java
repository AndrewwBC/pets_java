package com.example.pets4ever.storie.Response;

import java.time.LocalDateTime;

public record StorieResponse(
        String fileUrl,
        String userProfileImgUrl,
        String username,
        LocalDateTime expiresAt
)
{}
