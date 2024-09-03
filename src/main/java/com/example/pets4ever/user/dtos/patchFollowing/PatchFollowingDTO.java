package com.example.pets4ever.user.dtos.patchFollowing;

public record PatchFollowingDTO (
        String idOfUserToBeFollowed,
        String actionUserId
)
{}
