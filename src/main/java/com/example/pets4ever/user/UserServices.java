package com.example.pets4ever.user;


import com.example.pets4ever.Infra.TokenService;
import com.example.pets4ever.aws.AmazonClient;
import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.DTO.*;
import com.example.pets4ever.user.validations.login.LoginValidate;
import com.example.pets4ever.user.validations.register.RegisterValidate;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServices {

    @Value("${ADM_MAIL}")
    private String admMail;
    private UserRole userRole;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    List<LoginValidate> loginValidate;

    @Autowired
    List<RegisterValidate> registerValidate;

    private final AmazonClient amazonClient;
    @Autowired
    UserServices(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    public LoginResponseDTO login(UserAuthDTO userAuthDTO) {
        this.loginValidate.forEach(v -> v.validate(userAuthDTO));

        var usernamePassword = new UsernamePasswordAuthenticationToken(userAuthDTO.email(), userAuthDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        String userId = this.tokenService.validateTokenAndGetUserId(token);

        User user = this.userRepository.findById(userId).get();

        return LoginResponseDTO.fromUserAndToken(user, token);
    }
    public ProfileDTO profile(String userId) {

            List<Post> posts = userRepository.getPostsByUserId(userId);
            ProfileDTO profileResponseData = new ProfileDTO(posts);

            System.out.println(posts);

            return profileResponseData;
    }
    public User register(RegisterDTO registerDTO) {

        this.registerValidate.forEach(v -> v.validate(registerDTO));

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

        //this.validate.updateValidate(updateDTO);

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

    public User changeProfilePicture(ProfileImg profileImg, String userId) {

        Optional<User> user = this.userRepository.findById(userId);

        String pictureUrl = this.amazonClient.uploadFile(profileImg.getFile());
        System.out.println(pictureUrl);

        if(user.isPresent()) {
            user.get().setUserProfilePhotoUrl(pictureUrl);
            userRepository.save(user.get());
            return user.get();
        }

        return null;
    }

}























