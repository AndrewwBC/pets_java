package com.example.pets4ever.infra.exceptions.sql;


import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.engine.jdbc.env.spi.SQLStateType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class SqlExceptionsHandler {

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<?> handlePersistenceException(PersistenceException persistenceException) {
        Throwable cause = persistenceException.getCause();
        System.out.println(cause);

        if(cause instanceof ConstraintViolationException constraintViolationException) {
            List<ErrorListDTO> response = constraintViolationException.getConstraintViolations().stream().map((error)
                    -> new ErrorListDTO(error.getPropertyPath().toString(),error.getMessage())).toList();
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.badRequest().body(persistenceException.getMessage());
    }
}
