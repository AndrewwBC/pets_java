package com.example.pets4ever.infra.exceptions.email;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<String> handler(EmailAlreadyInUseException emailAlreadyInUseException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emailAlreadyInUseException.getMessage());
    }

}
