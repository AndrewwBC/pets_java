package com.example.pets4ever.user.validations.login;

import com.example.pets4ever.user.dtos.signInDTO.SignInDTO;

public interface SignInValidate {
    void checkEmailExists(SignInDTO signInDTO);

    void checkPassword(SignInDTO signInDTO);

    void allValidations(SignInDTO signInDTO);
}
