package com.example.pets4ever.controllers;

import com.example.pets4ever.domain.user.RegisterDTO;
import com.example.pets4ever.domain.user.UserAuthDTO;
import com.example.pets4ever.domain.user.User;
import com.example.pets4ever.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserAuthDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.name(), data.password());
        System.out.println(usernamePassword);
        var auth = this.authenticationManager.authenticate(usernamePassword);

        System.out.println(auth);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {

        if(this.userRepository.findByName(data.name()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), encryptedPassword, data.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }


}
