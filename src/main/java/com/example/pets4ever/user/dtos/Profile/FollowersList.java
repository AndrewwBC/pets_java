package com.example.pets4ever.user.dtos.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class FollowersList {

    private String name;

    @Override
    public String toString() {
        return "FollowersList{" +
                "name='" + name + '\'' +
                '}';
    }
}
