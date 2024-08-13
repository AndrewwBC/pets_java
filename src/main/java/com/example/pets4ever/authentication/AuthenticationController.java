package com.example.pets4ever.authentication;

import com.example.pets4ever.infra.security.TokenService;
import com.example.pets4ever.user.dtos.signInDTO.SignInDTO;
import com.example.pets4ever.user.dtos.signInDTO.SignInResponseDTO;
import com.example.pets4ever.user.dtos.signInDTO.SignInWithSessionDTO;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserService;
import com.example.pets4ever.user.validations.login.SignInValidate;
import com.example.pets4ever.utils.GetUserIdFromToken;
import com.example.pets4ever.utils.RecoverTokenFromHeaderWithoutBearer;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    GetUserIdFromToken getUserIdFromToken;
    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userService;
    @Autowired
    SignInValidate signInValidate;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    RecoverTokenFromHeaderWithoutBearer recoverTokenFromHeaderWithoutBearer;

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDTO> signin(@RequestBody @Valid SignInDTO data) {
        this.signInValidate.allValidations(data);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        var userId = tokenService.validateTokenAndGetUserId(token);

        User user = this.userService.signin(userId);

        return ResponseEntity.ok(new SignInResponseDTO(userId, user.getUsername(), user.getEmail(), user.getUserProfilePhotoUrl(), token));
    }

    @PostMapping("/loginwithsession")
    public ResponseEntity<SignInWithSessionDTO> loginWithSession(@RequestHeader("Authorization") String bearerToken) {
        String userId = getUserIdFromToken.recoverUserId(bearerToken);
        User user = this.userService.signin(userId);
        return ResponseEntity.ok(new SignInWithSessionDTO(userId, user.getUsername(), user.getEmail(), user.getUserProfilePhotoUrl()));
    }

}
