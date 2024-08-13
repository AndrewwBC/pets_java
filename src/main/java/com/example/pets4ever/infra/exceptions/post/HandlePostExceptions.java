package com.example.pets4ever.infra.exceptions.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlePostExceptions {
    @ExceptionHandler(WrongFileType.class)
    public ResponseEntity<?> handler(WrongFileType wrongFileType){
        return ResponseEntity.badRequest().body(wrongFileType.getMessage());
    }

}
