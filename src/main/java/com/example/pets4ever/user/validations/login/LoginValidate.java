package com.example.pets4ever.user.validations.login;

import com.example.pets4ever.user.dtos.SignIn.SignInDTO;

public interface LoginValidate {
    void validate(SignInDTO userAuthDTO);
}
