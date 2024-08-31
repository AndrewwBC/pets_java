package com.example.pets4ever.infra.exceptions.sql;


import jakarta.persistence.PersistenceException;
import org.hibernate.engine.jdbc.env.spi.SQLStateType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class SqlExceptionsHandler {

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<String> handlePersistenceException(PersistenceException persistenceException) {
        return ResponseEntity.badRequest().body(persistenceException.getMessage());
    }
}
