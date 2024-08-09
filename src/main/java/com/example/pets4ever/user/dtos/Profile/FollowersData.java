package com.example.pets4ever.user.dtos.Profile;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FollowersData {
    private List<FollowersList> followersList;
    private long quantityOfFollowers;


    @Override
    public String toString() {
        return "FollowersData{" +
                "followersList=" + followersList +
                ", quantityOfFollowers='" + quantityOfFollowers + '\'' +
                '}';
    }
}
