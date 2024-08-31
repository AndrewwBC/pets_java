package com.example.pets4ever.comment.DTO;

import com.example.pets4ever.user.User;
import lombok.*;


public record CommentPostResponseDTO (
        String userId,
        String profileImgUrl,
        String username,
        String comment
){
    public static CommentPostResponseDTO fromData(User user, String comment){
        return new CommentPostResponseDTO(
            user.getId(), user.getProfileImgUrl(), user.getUsername(),comment
        );
    }
}
