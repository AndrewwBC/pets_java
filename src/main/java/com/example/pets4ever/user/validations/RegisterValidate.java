package com.example.pets4ever.user.validations;


import com.example.pets4ever.Infra.exception.user.CustomValidationException;
import com.example.pets4ever.user.DTO.Register.RegisterDTO;
import com.example.pets4ever.user.UserRepository;
import com.example.pets4ever.Infra.exception.dto.ErrorListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegisterValidate implements Validate {
    @Autowired
    UserRepository userRepository;

    @Override
    public void registerValidate(RegisterDTO registerDTO) {
        List<ErrorListDTO> registerErrors = new ArrayList<>();

        if(this.userRepository.findByName(registerDTO.getName()) != null) {
            registerErrors.add(new ErrorListDTO( "username", "Nome de usuário já cadastrado!"));
        }
        if (this.userRepository.findByEmail(registerDTO.getEmail()) != null) {
            registerErrors.add(new ErrorListDTO("email", "Email já cadastrado!"));
        }
        if(!registerErrors.isEmpty()){
           throw new CustomValidationException(registerErrors);
        }
    }
}
