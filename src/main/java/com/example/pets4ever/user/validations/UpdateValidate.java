package com.example.pets4ever.user.validations;


import com.example.pets4ever.Infra.exception.user.CustomValidationException;
import com.example.pets4ever.Infra.exception.user.ErrorListDTO;
import com.example.pets4ever.user.DTO.UpdateDTO;
import com.example.pets4ever.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UpdateValidate implements Validate{

    @Autowired
    UserRepository userRepository;

    @Override
    public void updateValidate(UpdateDTO updateDTO) {

        List<ErrorListDTO> errorListDTO = new ArrayList<>();

        if(this.userRepository.findByName(updateDTO.name()) != null) {
            errorListDTO.add(new ErrorListDTO("name", "Nome já cadastrado!"));
        }

        if(this.userRepository.findByEmail(updateDTO.email()) != null) {
            errorListDTO.add(new ErrorListDTO("email", "Email já cadastrado!"));
        }

        if(!errorListDTO.isEmpty()){
            throw new CustomValidationException(errorListDTO);
        }

    }
}













