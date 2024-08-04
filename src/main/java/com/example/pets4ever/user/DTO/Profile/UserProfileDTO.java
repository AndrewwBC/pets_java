package com.example.pets4ever.user.DTO.Profile;


import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


public record UserProfileDTO (
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





