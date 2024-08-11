package com.example.pets4ever.user.validations.update;


import com.example.pets4ever.infra.exceptions.user.validation.CustomValidationException;
import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import com.example.pets4ever.user.dtos.updateDTO.UpdateDTO;
import com.example.pets4ever.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateValidations implements UpdateValidate {

    @Autowired
    UserRepository userRepository;

    @Override
    public void validate(UpdateDTO updateDTO) {

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













