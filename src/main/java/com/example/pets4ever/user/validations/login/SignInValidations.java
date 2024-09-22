package com.example.pets4ever.user.validations.login;

import com.example.pets4ever.infra.exceptions.user.validation.SigninException;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import com.example.pets4ever.user.dtos.UserSignInDTO.UserSignInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SignInValidations implements SignInValidate {

    @Autowired
    UserRepository userRepository;

    @Override
    public void validate(UserSignInDTO userSignInDTO) {

        User user = this.userRepository.findByEmail(userSignInDTO.email()).orElseThrow(()
                -> new SigninException("Email ou senha incorretos."));

        String cryptedPassword = user.getPassword();
        String loginPassword = userSignInDTO.password();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(!encoder.matches(loginPassword, cryptedPassword)) {
            throw new SigninException("Email ou senha incorretos.");
        }
    }
}
