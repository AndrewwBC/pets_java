package com.example.pets4ever.infra.exceptions.user;


import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import com.example.pets4ever.infra.exceptions.user.validation.UserValidationsException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionsOfUserFeatureHandler {

    @ExceptionHandler(UserValidationsException.class)
    public ResponseEntity<List<ErrorListDTO>> handleExceptionsThrownByAllValidations(UserValidationsException customRegisterException){
        return ResponseEntity.badRequest().body(customRegisterException.registerErrors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ErrorListDTO>> handleConstraintViolationException(ConstraintViolationException exception) {
        List<ErrorListDTO> messages =  new ArrayList<>();

        exception.getConstraintViolations().forEach(constraintViolation -> {
            messages.add(new ErrorListDTO(constraintViolation.getPropertyPath().toString()
                    ,constraintViolation.getMessage()));
        });

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(messages);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleResourceNotFoundException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
