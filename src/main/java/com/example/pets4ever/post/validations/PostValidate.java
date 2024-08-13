package com.example.pets4ever.post.validations;
import com.example.pets4ever.post.DTO.PostDTO;

public interface PostValidate {
    void fileFormat(PostDTO postDTO);

    void allValidations(PostDTO postDTO);
}
