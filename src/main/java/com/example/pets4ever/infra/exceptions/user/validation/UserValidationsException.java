package com.example.pets4ever.infra.exceptions.user.validation;

import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import jakarta.validation.ValidationException;

import java.util.List;

public class UserValidationsException extends ValidationException {
    public List<ErrorListDTO> registerErrors;
    public UserValidationsException(List<ErrorListDTO> registerErrors) {
        this.registerErrors = registerErrors;
    }
}
