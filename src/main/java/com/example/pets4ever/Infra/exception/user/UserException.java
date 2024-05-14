package com.example.pets4ever.Infra.exception.user;


import jakarta.validation.ValidationException;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class UserException {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> validationException(ValidationException validationException){
        System.out.println(validationException.getMessage());
        return ResponseEntity.badRequest().body(validationException.getMessage());
    }


}
