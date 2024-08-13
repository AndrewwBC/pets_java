package com.example.pets4ever.infra.exceptions.PersistenceException;


import jakarta.persistence.PersistenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PersistenceExceptionHandler {

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<String> handler(PersistenceException persistenceException) {
        return ResponseEntity.badRequest().body(persistenceException.getMessage());
    }
}
