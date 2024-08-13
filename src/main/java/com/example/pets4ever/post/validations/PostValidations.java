package com.example.pets4ever.post.validations;

import com.example.pets4ever.infra.exceptions.post.WrongFileType;
import com.example.pets4ever.post.DTO.PostDTO;
import org.springframework.stereotype.Component;

@Component
public class PostValidations implements PostValidate{
    @Override
    public void fileFormat(PostDTO postDTO) {

        String fileFormat = postDTO.file().getContentType();

        System.out.println(fileFormat);

        if (!("image/jpeg".equals(fileFormat) || "image/jpg".equals(fileFormat) || "image/png".equals(fileFormat))) {
            throw new WrongFileType("Formato inválido, utilize JPEG, JPG ou PNG!");
        }

    }

    @Override
    public void allValidations(PostDTO postDTO) {
        this.fileFormat(postDTO);
    }
}
