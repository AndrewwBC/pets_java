package com.example.pets4ever.user;

import com.example.pets4ever.Infra.TokenService;
import com.example.pets4ever.user.DTO.*;
import com.example.pets4ever.utils.RecoverTokenFromHeaderWithoutBearer;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;
    @Autowired
    UserServices userServices;
    @Autowired
    RecoverTokenFromHeaderWithoutBearer recoverTokenFromHeaderWithoutBearer;
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserAuthDTO data) {

        var token = this.userServices.login(data);

       return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO data) {
         return ResponseEntity.ok().body(userServices.register(data));
    }
    @GetMapping("/profile")
    public ResponseEntity profile(@RequestHeader("Authorization") String bearerToken) {

        System.out.println(bearerToken);
        String token = recoverTokenFromHeaderWithoutBearer.token(bearerToken);
        String userId = tokenService.validateTokenAndGetUserId(token);

        return ResponseEntity.status(HttpStatus.OK).body(userServices.profile(userId));
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestHeader("Authorization") String bearerToken, @RequestBody UpdateDTO updateDTO){

        String token = recoverTokenFromHeaderWithoutBearer.token(bearerToken);
        String userId = tokenService.validateTokenAndGetUserId(token);

        User userUpdated = userServices.update(updateDTO, userId);

        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> delete(@RequestHeader("Authorization") String bearerToken){

        String token = recoverTokenFromHeaderWithoutBearer.token(bearerToken);
        String userId = tokenService.validateTokenAndGetUserId(token);

        System.out.println(userId);

        User deletedUser = userServices.delete(userId);

        return ResponseEntity.status(HttpStatus.OK).body(deletedUser);
    }

}
