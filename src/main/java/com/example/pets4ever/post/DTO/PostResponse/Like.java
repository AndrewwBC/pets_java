package com.example.pets4ever.post.DTO.PostResponse;

import com.example.pets4ever.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class Like {

    private String userId;
    private String username;
    private String userProfilePhotoUrl;

    public static Like fromUser(User user) {
        return new Like(
                user.getId(),
                user.getName(),
                user.getUserProfilePhotoUrl()
        );
    }
}
