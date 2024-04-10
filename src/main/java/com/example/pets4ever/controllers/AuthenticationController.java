package com.example.pets4ever.controllers;

import com.example.pets4ever.Infra.TokenService;
import com.example.pets4ever.user.*;
import com.example.pets4ever.user.DTO.LoginResponseDTO;
import com.example.pets4ever.user.DTO.RegisterDTO;
import com.example.pets4ever.user.DTO.UserAuthDTO;
import com.example.pets4ever.user.UserRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.pets4ever.user.error.RegisterError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Value("${ADM_MAIL}")
    private String admMail;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    TokenService tokenService;
    private UserRole userRole;
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserAuthDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {

        List<RegisterError> registerErrors = new ArrayList<>();

        if(this.userRepository.findByName(data.name()) != null) {
            registerErrors.add(new RegisterError( "username", "Nome de usuário já cadastrado!"));
        }
        if (this.userRepository.findByEmail(data.email()) != null) {
            registerErrors.add(new RegisterError("email", "Email já cadastrado!"));
        };
        if(!registerErrors.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(registerErrors);
        }

        if(Objects.equals(data.email(), admMail)) {
            this.userRole = UserRole.ADMIN;
        } else {
            this.userRole = UserRole.USER;
        }

        if(data.password().isEmpty()) {
            User newUser = new User(data.name(), data.email(), data.password(), this.userRole);
            this.userRepository.save(newUser);
        } else {
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            User newUser = new User(data.name(), data.email(), encryptedPassword, this.userRole);
            this.userRepository.save(newUser);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("CADASTRO REALIZADO COM SUCESSO!");
    }
}
