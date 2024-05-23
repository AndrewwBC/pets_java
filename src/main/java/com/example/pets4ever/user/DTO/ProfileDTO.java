package com.example.pets4ever.user.DTO;


import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProfileDTO {
    private String id;
    private String username;
    private String email;
    private String profileImageUrl;
    private List<User> followers;
    private List<User> following;
    private List<Post> posts;

    public ProfileDTO(String id, String username, String email,String profileImageUrl ,List<User> followers, List<User> following, List<Post> posts) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.followers = followers;
        this.following = following;
        this.posts = posts;
    }
}
