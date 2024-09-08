package com.example.pets4ever.authentication;

import com.example.pets4ever.infra.security.TokenService;
import com.example.pets4ever.user.dtos.signInDTO.SignInDTO;
import com.example.pets4ever.user.dtos.signInDTO.SignInWithSessionDTO;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserService;
import com.example.pets4ever.user.validations.login.SignInValidate;
import com.example.pets4ever.utils.GetUserIdFromToken;
import com.example.pets4ever.utils.MyCookie;
import com.example.pets4ever.utils.RecoverTokenFromHeaderWithoutBearer;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<?> signin(@RequestBody @Valid SignInDTO data, HttpServletResponse response) {
        this.signInValidate.validate(data);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        String jwt = tokenService.generateToken((User) auth.getPrincipal());
        System.out.println(jwt);

        MyCookie myCookie = new MyCookie();

        Cookie jwtCookie = myCookie.generateCookie("jwt", jwt, 24, true, true);
        Cookie hasSession = myCookie.generateCookie("hasSession", "yes", 24, false, false);

        response.addCookie(jwtCookie);
        response.addCookie(hasSession);

        return ResponseEntity.ok("Logado com sucesso.");
    }

    @GetMapping("/session")
    public ResponseEntity<SignInWithSessionDTO> loginWithSession(HttpServletRequest request) {
        String userId = getUserIdFromToken.recoverUserId(request);
        User user = this.userService.signin(userId);
        return ResponseEntity.ok(new SignInWithSessionDTO(userId, user.getUsername(), user.getEmail(), user.getProfileImgUrl()));
    }

}
