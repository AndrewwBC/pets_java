package com.example.pets4ever.user.validations.login;

import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import com.example.pets4ever.infra.exceptions.user.validation.SigninException;
import com.example.pets4ever.infra.exceptions.user.validation.UserValidationsException;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import com.example.pets4ever.user.dtos.signInDTO.SignInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class SignInValidations implements SignInValidate {

    @Autowired
    UserRepository userRepository;

    @Override
    public void validate(SignInDTO signInDTO) {

        User user = this.userRepository.findByEmail(signInDTO.email()).orElseThrow(()
                -> new SigninException("Email ou senha incorretos."));

        String cryptedPassword = user.getPassword();
        String loginPassword = signInDTO.password();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(!encoder.matches(loginPassword, cryptedPassword)) {
            throw new SigninException("Email ou senha incorretos.");
        }
    }
}
