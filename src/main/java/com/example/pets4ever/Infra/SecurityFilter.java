package com.example.pets4ever.Infra;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.pets4ever.Infra.exception.TokenExpired.MyTokenExpiredException;
import com.example.pets4ever.Infra.exception.TokenExpired.MyTokenExpiredExceptionHandler;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyTokenExpiredExceptionHandler myTokenExpiredExceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            var token = this.recoverToken(request);

            if(token != null) {
                try {
                    String subject = tokenService.validateTokenAndGetUserId(token);
                    Optional<User> user = userRepository.findById(subject);

                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.get().getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (JWTVerificationException exception) {
                    System.out.println(exception.getMessage());
                    myTokenExpiredExceptionHandler.handleTokenExpiredException(response, exception);
                    return;
                }
            }
            filterChain.doFilter(request, response);

    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        System.out.println("Recover Token");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer", "").replace(" ", "");
    }
}
