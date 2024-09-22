package com.example.pets4ever.user.validations.create;

import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import com.example.pets4ever.infra.exceptions.user.validation.UserValidationsException;
import com.example.pets4ever.user.dtos.SignInDTO;
import com.example.pets4ever.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateValidations implements CreateValidate {

    @Autowired
    UserRepository userRepository;
    @Override
    public void validate(SignInDTO signupDTO) {

        boolean usernameAlreadyInUse = userRepository.existsByUsername(signupDTO.getUsername());
        boolean emailAlreadyInUse = userRepository.existsByEmail(signupDTO.getEmail());

        List<ErrorListDTO> errorList = new ArrayList<>();

        if(usernameAlreadyInUse){
            errorList.add(new ErrorListDTO("username", "Nome de usuário já cadastrado."));
        }
        if(emailAlreadyInUse){
            errorList.add(new ErrorListDTO("email", "Email já cadastrado."));
        }

        if(!errorList.isEmpty()){
            throw new UserValidationsException(errorList);
        }
    }
}