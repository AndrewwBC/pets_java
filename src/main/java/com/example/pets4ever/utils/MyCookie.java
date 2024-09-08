package com.example.pets4ever.utils;
import org.springframework.http.ResponseCookie;


public class MyCookie {

    public ResponseCookie generateCookie(String name, String value, Integer age, boolean httpOnly) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(httpOnly)
                .secure(true) // Set true for production, HTTPS only
                .sameSite("strict")
                .maxAge(age * 60 * 60)
                .domain("andrewcampos.site") // Set for production
                .path("/")
                .build();
        return cookie;
    }

}
