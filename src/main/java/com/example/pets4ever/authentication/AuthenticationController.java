package com.example.pets4ever.authentication;

import com.example.pets4ever.Infra.TokenService;
import com.example.pets4ever.user.dtos.SignIn.SignInDTO;
import com.example.pets4ever.user.dtos.SignIn.SignInResponseDTO;
import com.example.pets4ever.user.dtos.SignIn.SignInWithSessionDTO;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserServices;
import com.example.pets4ever.user.validations.login.LoginValidate;
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
    UserServices userServices;
    @Autowired
    List<LoginValidate> loginValidate;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    RecoverTokenFromHeaderWithoutBearer recoverTokenFromHeaderWithoutBearer;

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDTO> signin(@RequestBody @Valid SignInDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        var userId = tokenService.validateTokenAndGetUserId(token);

        User user = this.userServices.signin(userId);

        return ResponseEntity.ok(new SignInResponseDTO(userId, user.getUsername(), user.getEmail(), user.getUserProfilePhotoUrl(), token));
    }

    @PostMapping("/loginwithsession")
    public ResponseEntity<SignInWithSessionDTO> loginWithSession(@RequestHeader("Authorization") String bearerToken) {
        String userId = getUserIdFromToken.recoverUserId(bearerToken);
        User user = this.userServices.signin(userId);
        return ResponseEntity.ok(new SignInWithSessionDTO(userId, user.getUsername(), user.getEmail(), user.getUserProfilePhotoUrl()));
    }

}