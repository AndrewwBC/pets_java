package com.example.pets4ever.infra.exceptions.user.validation;

public class SigninException extends RuntimeException{

    private String message;
    public SigninException(String message){
        super(message);
    }
}
