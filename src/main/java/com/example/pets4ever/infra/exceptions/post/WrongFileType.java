package com.example.pets4ever.infra.exceptions.post;

import jakarta.validation.ValidationException;

public class WrongFileType extends ValidationException {

    public WrongFileType(String message) {
        super(message);
    }
}
