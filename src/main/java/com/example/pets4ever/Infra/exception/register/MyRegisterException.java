package com.example.pets4ever.Infra.exception.register;

import com.example.pets4ever.Infra.exception.dto.ErrorListDTO;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MyRegisterException extends ValidationException {

    private List<ErrorListDTO> registerErrors = new ArrayList<>();
    public MyRegisterException(List<ErrorListDTO> registerErrors ){
        this.registerErrors = registerErrors;
    }
    @ExceptionHandler(MyRegisterException.class)
    public ResponseEntity<List<ErrorListDTO>> handler(MyRegisterException myRegisterException){
        return ResponseEntity.badRequest().body(myRegisterException.registerErrors);
    }
}
