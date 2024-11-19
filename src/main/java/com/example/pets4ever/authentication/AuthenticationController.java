package com.example.pets4ever.authentication;

import com.example.pets4ever.infra.security.TokenService;
import com.example.pets4ever.user.dtos.SignInDTO;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserService;
import com.example.pets4ever.user.validations.UserValidations;
import com.example.pets4ever.utils.GetIdFromToken;
import com.example.pets4ever.utils.MyCookie;
import com.example.pets4ever.utils.RecoverTokenFromHeaderWithoutBearer;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    GetIdFromToken getIdFromToken;
    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userService;
    @Autowired
    UserValidations userValidations;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    RecoverTokenFromHeaderWithoutBearer recoverTokenFromHeaderWithoutBearer;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid SignInDTO data) {
        this.userValidations.signInValidate(data);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        String jwt = tokenService.generateToken((User) auth.getPrincipal());

        MyCookie myCookie = new MyCookie();

        ResponseCookie jwtCookie = myCookie.generateCookie("jwt", jwt, 24, true, true);
        ResponseCookie hasSession = myCookie.generateCookie("hasSession", "yes", 24, false, false);

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, hasSession.toString());

        return ResponseEntity.ok().headers(headers).body("Logado com sucesso.");
    }
}
