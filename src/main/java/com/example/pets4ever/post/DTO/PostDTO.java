package com.example.pets4ever.post.DTO;

import com.example.pets4ever.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class PostDTO {

    private String description;

    private MultipartFile file;

    private String creationDate;

    private String isStorie;
    public PostDTO(String description,MultipartFile file , String creationDate, String isStorie) {
        this.description = description;
        this.file = file;
        this.creationDate = creationDate;
        this.isStorie = isStorie;
    }
}
