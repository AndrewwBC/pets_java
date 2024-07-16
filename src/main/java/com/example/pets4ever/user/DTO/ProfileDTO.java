package com.example.pets4ever.user.DTO;


import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ProfileDTO {
    private List<Post> posts;

    @Override
    public String toString() {
        return "ProfileDTO{" +
                "posts=" + posts +
                '}';
    }
}
