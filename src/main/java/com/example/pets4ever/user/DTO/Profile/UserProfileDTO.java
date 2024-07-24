package com.example.pets4ever.user.DTO.Profile;


import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


public record UserProfileDTO (

    String username,
    String email,
    String imageProfileUrl,
    FollowersData followers,
    List<FollowingsData> following,
    List<PostResponseDTO> posts
){
    public static UserProfileDTO fromData(User user, FollowersData followersData, List<FollowingsData> followingOfProfileDTOS, List<PostResponseDTO> postResponseDTOList) {
        return new UserProfileDTO(
                user.getUsername(),
                user.getEmail(),
                user.getUserProfilePhotoUrl(),
                followersData,
                followingOfProfileDTOS,
                postResponseDTOList
        );
    }
}





