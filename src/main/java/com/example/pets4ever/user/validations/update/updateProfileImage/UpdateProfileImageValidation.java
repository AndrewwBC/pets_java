package com.example.pets4ever.user.validations.update.updateProfileImage;

import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import com.example.pets4ever.infra.exceptions.user.validation.UserValidationsException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateProfileImageValidation implements UpdateProfileImageValidate {
    @Override
    public void validate(MultipartFile file) {

        List<ErrorListDTO> errorListDTOList = new ArrayList<>();

        if(file == null){
            errorListDTOList.add(new ErrorListDTO("image", "O campo imagem deve ser preenchido!"));
        }

        if(!errorListDTOList.isEmpty()) {
            throw new UserValidationsException(errorListDTOList);
        }
    }
}
