package com.example.pets4ever.Infra.exception.methodArgumentNotValidException;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

@AllArgsConstructor
@Setter
@Getter
public class MyArgumentNotValidExceptionResponseData {

    private String fieldName;
    private String message;

}
