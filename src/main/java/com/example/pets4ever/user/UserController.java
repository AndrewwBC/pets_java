package com.example.pets4ever.user;


import com.example.pets4ever.Infra.TokenService;
import com.example.pets4ever.user.DTO.ProfileDTO;
import com.example.pets4ever.utils.RecoverTokenFromHeaderWithoutBearer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/me")
public class UserController {

    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepo;

}
