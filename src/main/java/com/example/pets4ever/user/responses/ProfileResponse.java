package com.example.pets4ever.user.responses;

import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.dtos.profileDTO.FollowersData;
import com.example.pets4ever.user.dtos.profileDTO.FollowingsData;
import com.example.pets4ever.user.dtos.profileDTO.UserPostsAndQuantityOfPosts;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;


public record ProfileResponse (
        @NotBlank
        String username,
        @NotBlank
        @Email(message = "Email inválido!", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        String email,
        String userImageProfileUrl,
        FollowersData followers,
        FollowingsData following,
        UserPostsAndQuantityOfPosts userPostsAndQuantityOfPosts
){
    public static ProfileResponse fromData(User user, FollowersData followersData, FollowingsData followingOfProfileDTOS, UserPostsAndQuantityOfPosts userPostsAndQuantityOfPosts) {
        return new ProfileResponse(
                user.getUsername(),
                user.getEmail(),
                user.getProfileImgUrl(),
                followersData,
                followingOfProfileDTOS,
                userPostsAndQuantityOfPosts
        );
    }
}





