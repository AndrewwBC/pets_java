package com.example.pets4ever.Infra.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.pets4ever.Infra.exception.TokenExpired.MyTokenExpiredException;
import com.example.pets4ever.Infra.exception.dto.ErrorListDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler {
    @ExceptionHandler()
    public ResponseEntity handle(ConstraintViolationException exception) {

        List<ErrorListDTO> messages =  new ArrayList<>();

        exception.getConstraintViolations().forEach(constraintViolation -> {
            System.out.println(constraintViolation.getMessage());
            messages.add(new ErrorListDTO(constraintViolation.getPropertyPath().toString()
                    ,constraintViolation.getMessage()));
        });

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(messages);
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(JWTVerificationException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token inválido");
    }

}
