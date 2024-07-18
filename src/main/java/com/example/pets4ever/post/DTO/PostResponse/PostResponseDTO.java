package com.example.pets4ever.post.DTO.PostResponse;

import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostResponseDTO {

    private String postId;
    private String description;
    private String imageUrl;
    private String creationDate;
    private String name;
    private String userProfileImageUrl;
    private String userId;
    private boolean userLikedThisPost;
    private List<Like> listOfLikes;
    private List<CommentPostResponseDTO> comments;

    public static PostResponseDTO fromData(Post post, User user, boolean userLikedThisPost,List<Like> listOfLikes ,List<CommentPostResponseDTO> comments) {
        return new PostResponseDTO(
                post.getId(),
                post.getDescription(),
                post.getImageUrl(),
                post.getCreationDate(),
                user.getName(),
                user.getUserProfilePhotoUrl(),
                user.getId(),
                userLikedThisPost,
                listOfLikes,
                comments
        );
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