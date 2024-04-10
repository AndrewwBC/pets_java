package com.example.pets4ever.comment;


import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.User;
import jakarta.persistence.*;
import lombok.Getter;



@Entity
@Getter
public class  Comment {

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
