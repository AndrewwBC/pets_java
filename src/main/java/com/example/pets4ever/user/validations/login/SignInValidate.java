package com.example.pets4ever.user.validations.login;

import com.example.pets4ever.user.dtos.UserSignInDTO.UserSignInDTO;

public interface SignInValidate {
    void validate(UserSignInDTO userSignInDTO);
}
