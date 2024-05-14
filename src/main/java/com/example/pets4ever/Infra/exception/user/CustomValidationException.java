package com.example.pets4ever.Infra.exception.user;

import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CustomValidationException extends ValidationException {

    private List<ErrorListDTO> registerErrors;

    public CustomValidationException(List<ErrorListDTO> registerErrors) {
        this.registerErrors = registerErrors;
    }
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<List<ErrorListDTO>> handler(CustomValidationException customRegisterException){
        return ResponseEntity.badRequest().body(customRegisterException.registerErrors);
    }

}
