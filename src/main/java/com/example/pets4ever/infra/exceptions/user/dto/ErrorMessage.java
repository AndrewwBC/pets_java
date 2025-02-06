package com.example.pets4ever.infra.exceptions.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ErrorMessage {
    private String error;

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "error='" + error + '\'' +
                '}';
    }
}


