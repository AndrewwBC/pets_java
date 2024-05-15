package com.example.pets4ever.user.validations.login;

import com.example.pets4ever.user.DTO.UserAuthDTO;

public interface LoginValidate {
    void validate(UserAuthDTO userAuthDTO);
}
