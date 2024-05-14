package com.example.pets4ever.post;


import com.example.pets4ever.comment.Comment;
import com.example.pets4ever.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Posts")
@Table(name = "posts")
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="isStorie", discriminatorType = DiscriminatorType.STRING)

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String description;
    private String imageUrl;
    private String creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", user=" + user +
                ", likes=" + likes +
                '}';
    }
}
