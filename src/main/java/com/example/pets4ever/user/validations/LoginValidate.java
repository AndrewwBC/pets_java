package com.example.pets4ever.user.validations;

import com.example.pets4ever.Infra.exception.user.CustomValidationException;
import com.example.pets4ever.Infra.exception.user.ErrorListDTO;
import com.example.pets4ever.user.DTO.UserAuthDTO;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoginValidate implements Validate {

    @Autowired
    UserRepository userRepository;
    @Override
    public void loginValidate(String email) throws CustomValidationException {
        System.out.println("LoginValidate");

        List<ErrorListDTO> loginError = new ArrayList<>();
        loginError.add(new ErrorListDTO( "Email", "Conta não registrada!"));

        UserDetails userEmail = this.userRepository.findByEmail(email);

        System.out.println(userEmail);

        if(userEmail == null) {
            System.out.println("Entrou");
            throw new CustomValidationException(loginError);
        }
    }
}
