package com.example.pets4ever.user;


import com.example.pets4ever.Infra.TokenService;
import com.example.pets4ever.user.DTO.ProfileDTO;
import com.example.pets4ever.user.DTO.RegisterDTO;
import com.example.pets4ever.user.DTO.UpdateDTO;
import com.example.pets4ever.user.DTO.UserAuthDTO;
import com.example.pets4ever.user.validations.Validate;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServices {

    @Value("${ADM_MAIL}")
    private String admMail;
    private UserRole userRole;

    @Autowired
    @Qualifier("registerValidate")
    private Validate validate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String login(UserAuthDTO userAuthDTO) {
        this.validate.loginValidate(userAuthDTO.email());

        var usernamePassword = new UsernamePasswordAuthenticationToken(userAuthDTO.email(), userAuthDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return token;
    }
    public ProfileDTO profile(String userId) {
        User user = userRepository.findById(userId).get();

        ProfileDTO profileDTO = new ProfileDTO(user.getId(), user.getUsername(), user.getEmail(),user.getFollowing(), user.getFollowers(), user.getPosts());
        System.out.println(profileDTO);

        return profileDTO;
    }
    public User register(RegisterDTO registerDTO) {

        this.validate.registerValidate(registerDTO);

        if(Objects.equals(registerDTO.email(), admMail)) {
            this.userRole = UserRole.ADMIN;
        } else {
            this.userRole = UserRole.USER;
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

        User newUser = new User(registerDTO.name(), registerDTO.email(), encryptedPassword, userRole);
        return userRepository.save(newUser);
    }

    public User update(UpdateDTO updateDTO, String userId) {

        this.validate.updateValidate(updateDTO);

        Optional<User> user = userRepository.findById(userId);
        System.out.println(user);

        if(user.isPresent()) {

            User updatedUser = user.get();

            updatedUser.setName(updateDTO.name());
            updatedUser.setEmail(updateDTO.email());
            updatedUser.setPassword(updateDTO.password());

            return userRepository.save(updatedUser);
        }
        return null;
    }

    public User delete(String userId) {

        Optional<User> userToBeDeleted = this.userRepository.findById(userId);

        if(userToBeDeleted.isPresent()) {
            this.userRepository.delete(userToBeDeleted.get());
            return userToBeDeleted.get();
        }
        return null;

    }
}























