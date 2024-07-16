package com.example.pets4ever.comment.DTO;

import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CommentDTO {

    private String comment;
    private String postId;

    public CommentDTO(String comment, String postId){
        this.comment = comment;
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "comment='" + comment + '\'' +
                ", postId=" + postId +
                '}';
    }
}