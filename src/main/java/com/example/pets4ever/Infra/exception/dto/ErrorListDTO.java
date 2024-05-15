package com.example.pets4ever.Infra.exception.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorListDTO {
    private String fieldName;
    private String message;

    public ErrorListDTO(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message= message;
    }

    @Override
    public String toString() {
        return "RegisterError{" +
                "fieldName='" + fieldName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
