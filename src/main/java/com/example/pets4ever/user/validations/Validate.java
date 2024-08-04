package com.example.pets4ever.user.validations;

import com.example.pets4ever.user.DTO.Register.RegisterDTO;
import com.example.pets4ever.user.DTO.UpdateDTO.UpdateDTO;

public interface Validate {

    default void registerValidate(RegisterDTO registerDTO) {}

    default void updateValidate(UpdateDTO updateDTO) {}

    default void loginValidate(String email){}

}
