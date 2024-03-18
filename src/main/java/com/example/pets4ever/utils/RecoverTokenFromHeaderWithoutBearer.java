package com.example.pets4ever.utils;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class RecoverTokenFromHeaderWithoutBearer {
    public String token(String bearerToken) {
        return bearerToken.replace("Bearer", "").replace(" ", "");
    }
}
