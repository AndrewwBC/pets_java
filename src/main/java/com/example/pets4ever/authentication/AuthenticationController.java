package com.example.pets4ever.authentication;

import com.example.pets4ever.infra.security.TokenService;
import com.example.pets4ever.user.dtos.signInDTO.SignInDTO;
import com.example.pets4ever.user.responses.SignInResponse;
import com.example.pets4ever.user.dtos.signInDTO.SignInWithSessionDTO;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserService;
import com.example.pets4ever.user.validations.login.SignInValidate;
import com.example.pets4ever.utils.GetUserIdFromToken;
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
        var jwt = tokenService.generateToken((User) auth.getPrincipal());


        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 24 Horas

        Cookie cookieHasSession = new Cookie("hasSession", "yes");
        cookieHasSession.setHttpOnly(false);
        cookieHasSession.setSecure(false);
        cookieHasSession.setPath("/");
        cookieHasSession.setMaxAge(24 * 60 * 60);

        System.out.println(jwt);
        System.out.println(cookie);
        response.addCookie(cookie);
        response.addCookie(cookieHasSession);

        return ResponseEntity.ok("Logado com sucesso.");
    }

    @GetMapping("/session")
    public ResponseEntity<SignInWithSessionDTO> loginWithSession(HttpServletRequest request) {
        String userId = getUserIdFromToken.recoverUserId(request);
        User user = this.userService.signin(userId);
        return ResponseEntity.ok(new SignInWithSessionDTO(userId, user.getUsername(), user.getEmail(), user.getProfileImgUrl()));
    }

}
