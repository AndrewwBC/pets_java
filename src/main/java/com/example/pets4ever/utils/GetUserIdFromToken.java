package com.example.pets4ever.utils;


import com.example.pets4ever.infra.security.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUserIdFromToken {

    @Autowired
    TokenService tokenService;
    public String recoverUserId(HttpServletRequest request) {

        String jwt = null;

        // Pegando o cookie JWT
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    jwt = cookie.getValue();
                }
            }
        }

        return tokenService.validateTokenAndGetUserId(jwt);
    }
}
