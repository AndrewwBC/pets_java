package com.example.pets4ever.user.dtos.Profile;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FollowingsData {
    private String username;

    @Override
    public String toString() {
        return "FollowingOfProfileDTO{" +
                "username='" + username + '\'' +
                '}';
    }
}
