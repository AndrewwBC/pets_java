package com.example.pets4ever.storie.Response;

import java.time.LocalDateTime;

public record StorieResponse(
        String fileUrl,
        String profileImgUrl,
        String username,
        LocalDateTime expiresAt,
        Long views
)
{}
