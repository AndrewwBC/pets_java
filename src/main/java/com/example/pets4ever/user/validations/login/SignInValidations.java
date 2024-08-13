package com.example.pets4ever.user.validations.login;

import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import com.example.pets4ever.infra.exceptions.user.validation.UserValidationsException;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import com.example.pets4ever.user.dtos.signInDTO.SignInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SignInValidations implements SignInValidate {

    @Autowired
    UserRepository userRepository;

    @Override
    public void checkEmailExists(SignInDTO signInDTO) {

        Optional<User> user = Optional.ofNullable(this.userRepository.findByEmail(signInDTO.email()));

        if(user.isEmpty()) {
            List<ErrorListDTO> error = new ArrayList<>();
            error.add(new ErrorListDTO("email", "Email não cadastrado!"));

            throw new UserValidationsException(error);
        }
    }

    @Override
    public void checkPassword(SignInDTO signInDTO) {

        Optional<User> user = Optional.ofNullable(this.userRepository.findByEmail(signInDTO.email()));

        if(user.isPresent()) {
            String cryptedPassword = user.get().getPassword();
            String loginPassword = signInDTO.password();

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if(!encoder.matches(loginPassword, cryptedPassword)) {
                List<ErrorListDTO> error = new ArrayList<>();
                error.add(new ErrorListDTO("password", "Senha incorreta!"));
                throw new UserValidationsException(error);
            }
        }
    }

    @Override
    public void allValidations(SignInDTO signInDTO) {
            this.checkEmailExists(signInDTO);
            this.checkPassword(signInDTO);
    }
}
