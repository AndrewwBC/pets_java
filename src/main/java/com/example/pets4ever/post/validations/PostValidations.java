package com.example.pets4ever.post.validations;

import com.example.pets4ever.infra.exceptions.post.WrongFileType;
import com.example.pets4ever.post.DTO.PostDTO;
import org.springframework.stereotype.Component;

@Component
public class PostValidations implements PostValidate{
    @Override
    public void fileFormat(PostDTO postDTO) throws WrongFileType {

        String fileFormat = postDTO.file().getContentType();

        if(!fileFormat.contains("jpeg") || !fileFormat.contains("png")){
            throw new WrongFileType("Formato inválido, utilize JPEG ou PNG!");
        } else {return;}

    }

    @Override
    public void allValidations(PostDTO postDTO) throws WrongFileType {
        this.fileFormat(postDTO);
    }


}
