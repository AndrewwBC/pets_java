package com.example.pets4ever.user.dtos.patchFollowers;

public record DeleteFollowerDTO(
        String idOfUserToBeRemovedOfFollowersList,
        String actionUserId)
{}
