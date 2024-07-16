package com.example.pets4ever.user.DTO;

import com.example.pets4ever.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {

    private String username;
    private String email;
    private String userProfileImageUrl;
    private String token;

    public static LoginResponseDTO fromUserAndToken(User user, String token){
        return new LoginResponseDTO(user.getUsername(), user.getEmail(), user.getUserProfilePhotoUrl(), token);
    }

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", userProfileImageUrl='" + userProfileImageUrl + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

