package com.example.pets4ever.utils;
import jakarta.servlet.http.Cookie;


public class MyCookie {

    public Cookie generateCookie(String name, String value, Integer age, boolean HttpOnly, boolean setSecure) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(HttpOnly);
        cookie.setSecure(setSecure);
        cookie.setPath("/");
        cookie.setDomain("andrewcampos.site");
        cookie.setMaxAge(age * 60 * 60);

        return cookie;
    }

}
