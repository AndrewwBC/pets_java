package com.example.pets4ever.post.DTO;

import com.example.pets4ever.comment.Comment;
import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.user.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostResponseDTO {

    private String postId;
    private String description;
    private String imageUrl;
    private String creationDate;
    private String name;
    private String userProfileImageUrl;
    private String userId;
    private List<CommentPostResponseDTO> comments;


    public PostResponseDTO(String postId, String description, String imageUrl, String creationDate, String name,String userProfileImageUrl ,String userId, List<CommentPostResponseDTO> comments) {
        this.postId = postId;
        this.description = description;
        this.imageUrl = imageUrl;
        this.userProfileImageUrl = userProfileImageUrl;
        this.creationDate = creationDate;
        this.name = name;
        this.userId = userId;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "PostResponseDTO{" +
                "postId='" + postId + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", name='" + name + '\'' +
                ", userProfileImageUrl='" + userProfileImageUrl + '\'' +
                ", userId='" + userId + '\'' +
                ", comments=" + comments +
                '}';
    }
}
