package com.example.pets4ever.user.validations.register;

import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import com.example.pets4ever.infra.exceptions.user.register.MyRegisterException;
import com.example.pets4ever.user.dtos.signupDTO.RegisterDTO;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EmailOrUsernameAlreadyInUse implements RegisterValidate{

    @Autowired
    UserRepository userRepository;
    @Override
    public void validate(RegisterDTO registerDTO) {

        List<ErrorListDTO> error = new ArrayList<>();

        Optional<User> isUsernameRegistered = Optional.ofNullable(this.userRepository.findByName(registerDTO.getName()));
        if(isUsernameRegistered.isPresent()) {
            error.add(new ErrorListDTO("name", "Nome já cadastrado."));
        }

        Optional<User> isEmailRegistered = Optional.ofNullable(this.userRepository.findByEmail(registerDTO.getEmail()));
        if(isEmailRegistered.isPresent()) {
            error.add(new ErrorListDTO("email", "Email já cadastrado."));
        }

        if(!error.isEmpty()) {
            throw new MyRegisterException(error);
        }
    }
}
