package com.example.pets4ever.user.responses;

import com.example.pets4ever.user.User;


public record SignInResponse(

        String userId,
        String username,
        String email,
        String profileImgUrl,
        String token
) {
    public static SignInResponse fromUserAndToken(User user, String token){
        return new SignInResponse(user.getId(), user.getUsername(), user.getEmail(), user.getProfileImgUrl(), token);
    }
}

