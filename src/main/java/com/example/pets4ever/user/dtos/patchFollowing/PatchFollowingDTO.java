package com.example.pets4ever.user.dtos.patchFollowing;

public record PatchFollowingDTO (
        String usernameOfUserToBeFollowed,
        String actionUserUsername
)
{}
