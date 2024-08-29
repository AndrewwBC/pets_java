package com.example.pets4ever.infra.email.Code.handler;


import com.example.pets4ever.infra.email.Code.exceptions.ErrorMessage;
import com.example.pets4ever.infra.email.Code.exceptions.InvalidCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleInvalidCodeException {

    @ExceptionHandler(InvalidCodeException.class)
    public ResponseEntity<ErrorMessage> handler(InvalidCodeException invalidCodeException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidCodeException.getErrorMessage());
    }
}
