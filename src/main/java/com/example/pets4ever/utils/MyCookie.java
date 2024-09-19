package com.example.pets4ever.utils;
import org.springframework.http.ResponseCookie;


public class MyCookie {

    public ResponseCookie generateCookie(String name, String value, Integer age, boolean httpOnly, boolean secure) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(httpOnly)
                .secure(true) // Set true for production, HTTPS only
                .sameSite("Lax")
                .maxAge(age * 60 * 60)
                .domain(".localhost") // Set for production
                .path("/")
                .build();
        return cookie;
    }

}
