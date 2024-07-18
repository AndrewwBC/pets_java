package com.example.pets4ever.post.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePostToReceiveLikeDTO {
    private String postId;

    public UpdatePostToReceiveLikeDTO(String postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "UpdatePostToReceiveLikeDTO{" +
                "postId='" + postId + '\'' +
                '}';
    }
}
