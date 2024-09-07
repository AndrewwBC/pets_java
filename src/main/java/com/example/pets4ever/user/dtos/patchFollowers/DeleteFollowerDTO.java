package com.example.pets4ever.user.dtos.patchFollowers;

import jakarta.validation.constraints.NotNull;

public record DeleteFollowerDTO(

        @NotNull
        String idOfFollowerToBeRemoved,
        @NotNull
        String actionUserId)
{}
