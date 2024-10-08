package com.example.pets4ever.comment.DTO;

import com.example.pets4ever.comment.Comment;
import com.example.pets4ever.user.User;
import lombok.*;

import java.time.LocalDateTime;


public record CommentPostResponseDTO (
        String commentId,
        String userId,
        String profileImgUrl,
        String username,
        String comment,
        LocalDateTime creationDate
){
    public static CommentPostResponseDTO fromData(User user, Comment comment){
        return new CommentPostResponseDTO(
                comment.getId(), user.getId(), user.getProfileImgUrl(), user.getUsername(),comment.getComment(),comment.getCreationDate()
        );
    }
}
