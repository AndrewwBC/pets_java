package com.example.pets4ever.Infra.exception.TokenExpired;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@ControllerAdvice
public class MyTokenExpiredExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public void handleTokenExpiredException(HttpServletResponse response, Exception exception) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("Token expirado ou invalido. Logue novamente!");
    }

}
