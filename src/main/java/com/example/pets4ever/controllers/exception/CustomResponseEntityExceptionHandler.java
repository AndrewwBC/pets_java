package com.example.pets4ever.controllers.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity handle(ConstraintViolationException exception) {

        List<String> messages =  new ArrayList<>();

        exception.getConstraintViolations().forEach(constraintViolation -> {
            System.out.println(constraintViolation.getMessage());
            messages.add(constraintViolation.getMessage());
        });

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(messages);
    }
}
