package com.example.pets4ever.infra.exceptions.user;


import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import com.example.pets4ever.infra.exceptions.user.dto.ErrorMessage;
import com.example.pets4ever.infra.exceptions.user.validation.SigninException;
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
public class HandleUserExceptions {

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
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.getErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler(SigninException.class)
    public ResponseEntity<ErrorMessage> handleSigninException(SigninException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.getErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(PatchFollowingException.class)
    public ResponseEntity<ErrorMessage> handlePatchFollowingException(PatchFollowingException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.getErrorMessage(exception.getMessage()));

    }

    private ErrorMessage getErrorMessage(String message) {
        return new ErrorMessage(message);
    }

}
