package com.example.pets4ever.user.responses;

import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.dtos.profileDTO.FollowersData;
import com.example.pets4ever.user.dtos.profileDTO.FollowingsData;
import com.example.pets4ever.user.dtos.profileDTO.UserPostsAndQuantityOfPosts;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;


public record UserResponse (
        String username,
        String fullname,
        String raca,
        String bio,
        String userId,
        String email,
        String profileImgUrl,
        FollowersData followers,
        FollowingsData following,
        UserPostsAndQuantityOfPosts userPostsAndQuantityOfPosts
){
    public static UserResponse fromData(User user, FollowersData followersData, FollowingsData followingOfProfileDTOS, UserPostsAndQuantityOfPosts userPostsAndQuantityOfPosts) {
        return new UserResponse(
                user.getUsername(),
                user.getFullname(),
                user.getRaca(),
                user.getBio(),
                user.getId(),
                user.getEmail(),
                user.getProfileImgUrl(),
                followersData,
                followingOfProfileDTOS,
                userPostsAndQuantityOfPosts
        );
    }
}





