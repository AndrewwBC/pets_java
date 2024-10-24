package com.example.pets4ever.storie;


import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("STORIE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Storie extends Post {
    private Long views;
    private LocalDateTime expirationTime;
    public Storie(String description, String imageUrl, User user) {
        super(description, imageUrl, user);
        this.views = 0L;
        this.expirationTime = LocalDateTime.now().plusHours(24);
    }

    @Override
    public String toString() {
        return "Storie{" +
                "id='" + getId() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", imageUrl='" + getImageUrl() + '\'' +
                ", creationDate='" + getCreationDate() + '\'' +
                ", expirationTime='" + expirationTime + '\'' +
                '}';
    }
}
