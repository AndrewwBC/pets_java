package com.example.pets4ever.user.dtos.profileDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FollowersData {
    private List<UserIdNameAndImageProps> followersList;
    private long quantity;


    @Override
    public String toString() {
        return "FollowersData{" +
                "followersList=" + followersList +
                ", quantityOfFollowers='" + quantity + '\'' +
                '}';
    }
}
