package com.example.pets4ever.domain.comment;


import com.example.pets4ever.domain.post.Post;
import com.example.pets4ever.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;



@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
