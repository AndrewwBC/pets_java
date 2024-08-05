package com.example.pets4ever.Infra.exception.MethodArgumentNotValidException;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class MyArgumentNotValidExceptionResponseData {

    private String fieldName;
    private String message;

}
