package com.example.pets4ever.utils;


import com.example.pets4ever.Infra.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUserIdFromToken {

    @Autowired
    RecoverTokenFromHeaderWithoutBearer recoverTokenFromHeaderWithoutBearer;

    @Autowired
    TokenService tokenService;
    public String userId(String bearerToken) {
        String token = recoverTokenFromHeaderWithoutBearer.token(bearerToken);
        return tokenService.validateTokenAndGetUserId(token);
    }
}