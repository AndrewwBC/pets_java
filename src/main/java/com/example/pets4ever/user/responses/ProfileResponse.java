package com.example.pets4ever.user.responses;

import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.dtos.profileDTO.FollowersData;
import com.example.pets4ever.user.dtos.profileDTO.FollowingsData;
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
        List<FollowingsData> following,
        List<PostResponseDTO> posts
){
    public static ProfileResponse fromData(User user, FollowersData followersData, List<FollowingsData> followingOfProfileDTOS, List<PostResponseDTO> postResponseDTOList) {
        return new ProfileResponse(
                user.getUsername(),
                user.getEmail(),
                user.getUserProfilePhotoUrl(),
                followersData,
                followingOfProfileDTOS,
                postResponseDTOList
        );
    }
}





