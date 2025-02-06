package com.example.pets4ever.infra.email.Code.exceptions;

import jakarta.validation.ValidationException;

public class InvalidCodeException extends ValidationException {
    private ErrorMessage message;
    public InvalidCodeException(ErrorMessage message) {
        this.message = message;
    }

    public ErrorMessage getErrorMessage(){
        return this.message;
    }

}
