package com.example.pets4ever.Infra.exception.login;

import com.example.pets4ever.Infra.exception.dto.ErrorListDTO;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MyLoginException extends ValidationException {

    private List<ErrorListDTO> loginError;

    public MyLoginException(List<ErrorListDTO> loginError) {
        this.loginError = loginError;
    }
    @ExceptionHandler(MyLoginException.class)
    public ResponseEntity<List<ErrorListDTO>> handler(MyLoginException loginException){
        System.out.println(this.loginError);
        return ResponseEntity.badRequest().body(loginException.loginError);
    }

}
