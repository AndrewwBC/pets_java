package com.example.pets4ever.user.dtos;

public record PatchFollowingDTO (
        String usernameOfUserToBeFollowed,
        String actionUserUsername
)
{}
