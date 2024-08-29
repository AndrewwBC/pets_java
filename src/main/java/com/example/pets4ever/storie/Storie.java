package com.example.pets4ever.storie;


import com.example.pets4ever.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("isStorie")
@Getter
@Setter
@AllArgsConstructor
public class Storie extends Post {
    private Long views;
    private LocalDateTime expirationTime;
    public Storie() {
        super();
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
