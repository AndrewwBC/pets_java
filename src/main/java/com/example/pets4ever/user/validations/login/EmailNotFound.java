package com.example.pets4ever.user.validations.login;

import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import com.example.pets4ever.infra.exceptions.user.login.MyLoginException;
import com.example.pets4ever.user.dtos.signInDTO.SignInDTO;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EmailNotFound implements LoginValidate {

    @Autowired
    UserRepository userRepository;
    @Override
    public void validate(SignInDTO signInDTO) {

        Optional<User> user = Optional.ofNullable(this.userRepository.findByEmail(signInDTO.email()));

        if(user.isEmpty()) {
            List<ErrorListDTO> error = new ArrayList<>();
            error.add(new ErrorListDTO("email", "Email não cadastrado!"));

            throw new MyLoginException(error);
        }
    }
}
