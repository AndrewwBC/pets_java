package com.example.pets4ever.user.validations;

import com.example.pets4ever.user.DTO.RegisterDTO;
import com.example.pets4ever.user.DTO.UpdateDTO;

public interface Validate {

    default void registerValidate(RegisterDTO registerDTO) {}

    default void updateValidate(UpdateDTO updateDTO) {}

    default void loginValidate(String email){}

}
