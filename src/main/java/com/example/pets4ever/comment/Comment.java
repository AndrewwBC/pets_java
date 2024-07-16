package com.example.pets4ever.comment;


import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;


@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    public Comment(String comment, User user, Post post) {
        this.comment = comment;
        this.user = user;
        this.post = post;
    }
    public String getUserId() {
        return this.user.getId();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                "userId" + user.getId() + '\'' +
                "userProfileImage" + user.getUserProfilePhotoUrl() + '\''+
                '}';
    }
}
