package com.example.pets4ever.infra.exceptions.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.ControllerAdvice;

@AllArgsConstructor
@Getter
public class PatchFollowingException extends RuntimeException{
    private String message;
}
