package com.example.pets4ever.infra.exceptions.user.register;

import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MyRegisterException extends ValidationException {

    public List<ErrorListDTO> registerErrors = new ArrayList<>();
    public MyRegisterException(List<ErrorListDTO> registerErrors ){
        this.registerErrors = registerErrors;
    }

}
