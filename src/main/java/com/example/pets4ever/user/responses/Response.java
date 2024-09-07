package com.example.pets4ever.user.responses;

public record Response (String message)
{
    public static Response fromString(String message) {
        return new Response(message);
    }
}
