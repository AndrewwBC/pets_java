package com.example.pets4ever.post;


import com.example.pets4ever.comment.Comment;
import com.example.pets4ever.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Posts")
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="isStorie", discriminatorType = DiscriminatorType.STRING)

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String description;
    private String imageUrl;
    private String creationDate;

    @ManyToOne
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch=FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    public Post(String description, String imageUrl, String creationDate, User user){
        this.description = description;
        this.imageUrl = imageUrl;
        this.creationDate = creationDate;
        this.user = user;
    }


    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", likes=" + likes +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
