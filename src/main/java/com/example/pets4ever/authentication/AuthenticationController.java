package com.example.pets4ever.authentication;

import com.example.pets4ever.Infra.TokenService;
import com.example.pets4ever.user.DTO.*;
import com.example.pets4ever.user.DTO.Register.RegisterDTO;
import com.example.pets4ever.user.DTO.SignIn.SignInDTO;
import com.example.pets4ever.user.DTO.SignIn.SignInResponseDTO;
import com.example.pets4ever.user.DTO.SignIn.SignInWithSessionDTO;
import com.example.pets4ever.user.DTO.UpdateDTO.UpdateDTO;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserServices;
import com.example.pets4ever.user.validations.login.LoginValidate;
import com.example.pets4ever.utils.GetUserIdFromToken;
import com.example.pets4ever.utils.RecoverTokenFromHeaderWithoutBearer;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        System.out.println(data);
        this.loginValidate.forEach(v -> v.validate(data));

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
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid RegisterDTO data) {
         return ResponseEntity.ok().body(userServices.register(data));
    }

    @PostMapping("/profileimg")
    public ResponseEntity<User> profileImage(@ModelAttribute ProfileImg profileImg, @RequestHeader("Authorization") String bearerToken){
        System.out.println(profileImg);

        String userId = getUserIdFromToken.recoverUserId(bearerToken);

        User userWithNewProfileImage = userServices.changeProfilePicture(profileImg, userId);

        return ResponseEntity.ok().body(userWithNewProfileImage);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestHeader("Authorization") String bearerToken, @RequestBody UpdateDTO updateDTO){

        String userId = getUserIdFromToken.recoverUserId(bearerToken);

        User userUpdated = userServices.update(updateDTO, userId);

        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> delete(@RequestHeader("Authorization") String bearerToken){

        String userId = getUserIdFromToken.recoverUserId(bearerToken);

        System.out.println(userId);

        User deletedUser = userServices.delete(userId);

        return ResponseEntity.status(HttpStatus.OK).body(deletedUser);
    }

}
