package com.example.pets4ever.infra.exceptions.user.login;

import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class MyLoginException extends ValidationException {

    public List<ErrorListDTO> loginError;

    public MyLoginException(List<ErrorListDTO> loginError) {
        this.loginError = loginError;
    }


}
