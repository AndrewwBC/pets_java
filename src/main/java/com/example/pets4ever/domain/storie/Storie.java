package com.example.pets4ever.domain.storie;


import com.example.pets4ever.domain.post.Post;
import com.example.pets4ever.domain.user.User;
import jakarta.persistence.*;

@Entity
public class Storie extends Post {
    private Long views;
}
