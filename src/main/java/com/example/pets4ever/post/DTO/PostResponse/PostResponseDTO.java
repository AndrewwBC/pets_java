package com.example.pets4ever.post.DTO.PostResponse;

import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostResponseDTO {

    private String postId;
    private String description;
    private String imageUrl;
    private LocalDateTime creationDate;
    private String name;
    private String profileImgUrl;
    private String userId;
    private boolean userLikedThisPost;
    private Long quantityOfLikes;
    private List<Like> listOfLikes;
    private List<CommentPostResponseDTO> comments;

    public static PostResponseDTO fromData(Post post, User user, boolean userLikedThisPost,Long quantityOfLikes,List<Like> listOfLikes ,List<CommentPostResponseDTO> comments) {
        return new PostResponseDTO(
                post.getId(),
                post.getDescription(),
                post.getImageUrl(),
                post.getCreationDate(),
                user.getUsername(),
                user.getProfileImgUrl(),
                user.getId(),
                userLikedThisPost,
                quantityOfLikes,
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
                ", profileImgUrl='" + profileImgUrl + '\'' +
                ", userId='" + userId + '\'' +
                ", comments=" + comments +
                '}';
    }
}
