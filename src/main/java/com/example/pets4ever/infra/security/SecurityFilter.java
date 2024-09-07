package com.example.pets4ever.infra.security;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.pets4ever.infra.exceptions.tokenExpired.MyTokenExceptionHandler;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyTokenExceptionHandler myTokenExceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = recoverToken(request);

        System.out.println("Toookeen: " + jwt);

            if(jwt != null) {
                try {
                    String subject = tokenService.validateTokenAndGetUserId(jwt);
                    System.out.println(subject);
                    User user = userRepository.findById(subject).orElseThrow();

                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (JWTVerificationException exception) {
                    System.out.println(exception.getMessage());
                    myTokenExceptionHandler.handleTokenException(response);
                    return;
                }
            }

            filterChain.doFilter(request, response);

    }

    private String recoverToken(HttpServletRequest request) {

        String jwt = null;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    jwt = cookie.getValue();
                }
            }
        }

        return jwt;
    }

}
