package com.example.pets4ever.Infra.exception.MethodArgumentNotValidException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class MyArgumentNotValidException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<List<MyArgumentNotValidExceptionResponseData>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<MyArgumentNotValidExceptionResponseData> myArgumentNotValidExceptionResponseData =
                ex.getBindingResult().getAllErrors().stream().map(error ->{
                    System.out.println(error);
                    String fieldName = ((FieldError) error).getField();

                    return new MyArgumentNotValidExceptionResponseData(fieldName, error.getDefaultMessage());
        }).toList();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(myArgumentNotValidExceptionResponseData);
    }
}
