package com.example.pets4ever.infra.exceptions.TokenExpired;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@ControllerAdvice
public class MyTokenExceptionHandler {

    @ExceptionHandler(JWTVerificationException.class)
    public void handleTokenException(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("Token expirado ou invalido. Logue novamente!");
    }

}
