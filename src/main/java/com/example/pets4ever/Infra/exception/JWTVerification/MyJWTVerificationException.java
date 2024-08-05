package com.example.pets4ever.Infra.exception.JWTVerification;


import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyJWTVerificationException {

    @ExceptionHandler(JwtValidationException.class)
    @ResponseBody
    public ResponseEntity<String> handleJWTVerificationException(JwtValidationException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("HANDLEEER");
    }

    @ExceptionHandler(JWTDecodeException.class)
    @ResponseBody
    public ResponseEntity<String> handleJWTVerificationException(JWTDecodeException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("HANDLEEER");
    }

}
