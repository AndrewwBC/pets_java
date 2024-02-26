package com.example.pets4ever.controllers.error;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterError {
    private String fieldName;
    private String message;

    public RegisterError(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message= message;
    }
}
