package com.example.pets4ever.comment.DTO;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPostResponseDTO {

    private String userId;
    private String userProfileImageUrl;
    private String comment;
}
