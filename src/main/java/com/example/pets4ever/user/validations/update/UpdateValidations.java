package com.example.pets4ever.user.validations.update;


import com.example.pets4ever.infra.exceptions.user.validation.UserValidationsException;
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

        if(this.userRepository.findByUsername(updateDTO.name()) != null) {
            errorListDTO.add(new ErrorListDTO("userName", "Nome de usário já cadastrado!"));
        }

        if(this.userRepository.findByEmail(updateDTO.email()) != null) {
            errorListDTO.add(new ErrorListDTO("email", "Email já cadastrado!"));
        }

        if(!errorListDTO.isEmpty()){
            throw new UserValidationsException(errorListDTO);
        }

    }
}













