package com.example.pets4ever.user.DTO.Profile;


import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private String username;
    private String email;
    private String imageProfileUrl;
    private FollowersData followers;
    private List<FollowingsData> following;
    private List<PostResponseDTO> posts;

    public static ProfileDTO fromData(User user, FollowersData followers, List<FollowingsData> followingList, List<PostResponseDTO> posts){
        return new ProfileDTO(
                user.getUsername(),
                user.getEmail(),
                user.getUserProfilePhotoUrl(),
                followers,
                followingList,
                posts
        );
    }


    @Override
    public String toString() {
        return "ProfileDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", imageProfileUrl='" + imageProfileUrl + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", posts=" + posts +
                '}';
    }
}
