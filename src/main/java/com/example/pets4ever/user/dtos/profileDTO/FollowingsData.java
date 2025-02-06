package com.example.pets4ever.user.dtos.profileDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FollowingsData {
    private List<UserIdNameAndImageProps> followingList = new ArrayList<>();
    private Integer quantity;

    @Override
    public String toString() {
        return "FollowingsData{" +
                "quantity=" + quantity +
                ", userData=" + followingList +
                '}';
    }
}
