package com.example.pets4ever.domain.post;


import com.example.pets4ever.domain.comment.Comment;
import com.example.pets4ever.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Entity(name = "posts")
@Table(name = "posts")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String description;
    private String imageUrl;
    private String creationDate;

    @ManyToOne
    private User user;
}
