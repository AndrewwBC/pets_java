package com.example.pets4ever.infra.exceptions.user.validation;

import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

public class CustomValidationException extends ValidationException {
    public List<ErrorListDTO> registerErrors;
    public CustomValidationException(List<ErrorListDTO> registerErrors) {
        this.registerErrors = registerErrors;
    }
}
