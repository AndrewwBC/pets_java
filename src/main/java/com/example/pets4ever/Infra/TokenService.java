package com.example.pets4ever.Infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.pets4ever.Infra.exception.TokenExpired.MyTokenExpiredException;
import com.example.pets4ever.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(this.secret);

        Date expiresAt = new Date(System.currentTimeMillis() + 1000);

        try {
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getId())
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);
        }
        catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }

    }
    public String validateTokenAndGetUserId(String token) {
        Algorithm algorithm = Algorithm.HMAC256(this.secret);

        try {
            String userId = JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

            return userId;
        } catch (JwtValidationException exception) {
            throw new JWTVerificationException(exception.getMessage());
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
