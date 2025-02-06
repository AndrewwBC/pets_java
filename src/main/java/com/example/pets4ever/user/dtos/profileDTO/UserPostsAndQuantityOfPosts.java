package com.example.pets4ever.user.dtos.profileDTO;

import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserPostsAndQuantityOfPosts {

    private List<PostResponseDTO> posts;
    private Integer quantity;

}
