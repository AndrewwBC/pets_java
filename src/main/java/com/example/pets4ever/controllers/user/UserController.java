package com.example.pets4ever.controllers.user;


import com.example.pets4ever.Infra.Security.TokenService;
import com.example.pets4ever.controllers.user.DTO.ProfileDTO;
import com.example.pets4ever.domain.user.User;
import com.example.pets4ever.repositories.UserRepository;
import com.example.pets4ever.utils.RecoverTokenFromHeaderWithoutBearer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/me")
public class UserController {

    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepo;
    @Autowired
    RecoverTokenFromHeaderWithoutBearer recoverTokenFromHeaderWithoutBearer;
    @GetMapping("/profile")
    public ResponseEntity getUserData(@RequestHeader("Authorization") String bearerToken) {

        String token = recoverTokenFromHeaderWithoutBearer.token(bearerToken);
        String userId = tokenService.validateTokenAndGetUserId(token);

        Optional<User> getUserDataById = userRepo.findById(userId);

        User userData = getUserDataById.get();
        System.out.println(userData);

        ProfileDTO profileData = new ProfileDTO(userData.getId(), userData.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(profileData);
    }

}
