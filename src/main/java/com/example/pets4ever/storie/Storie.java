package com.example.pets4ever.storie;


import com.example.pets4ever.post.Post;
import jakarta.persistence.*;

@Entity
public class Storie extends Post {
    private Long views;
}
