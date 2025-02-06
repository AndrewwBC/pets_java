package com.example.pets4ever.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.pets4ever.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(this.secret);


        try {
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getId())
                    .withIssuedAt(Instant.now()) // Gerado em
                    .withExpiresAt(Instant.now().plus(Duration.ofHours(24))) // Expirado em
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
}
