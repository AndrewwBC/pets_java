package com.example.pets4ever.user;


import com.amazonaws.services.iot.model.ResourceNotFoundException;
import com.example.pets4ever.infra.security.TokenService;
import com.example.pets4ever.aws.AmazonClient;
import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.post.DTO.PostResponse.Like;
import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.dtos.changeProfileImageDTO.ProfileImg;
import com.example.pets4ever.user.dtos.profileDTO.FollowersData;
import com.example.pets4ever.user.dtos.profileDTO.FollowersList;
import com.example.pets4ever.user.dtos.profileDTO.FollowingsData;
import com.example.pets4ever.user.dtos.signupDTO.RegisterDTO;
import com.example.pets4ever.user.dtos.updateDTO.UpdateDTO;
import com.example.pets4ever.user.responses.ProfileResponse;
import com.example.pets4ever.user.validations.register.RegisterValidate;
import com.example.pets4ever.user.validations.update.UpdateValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${ADM_MAIL}")
    private String admMail;
    private UserRole userRole;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    List<RegisterValidate> registerValidate;

    @Autowired
    UpdateValidate updateValidate;
    private final AmazonClient amazonClient;
    @Autowired
    UserService(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    public ProfileResponse profile(String userId){

        User user =  this.userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        List<User> followers = user.getFollowers();
        List<User> following = user.getFollowing();
        List<Post> userPosts = user.getPosts();

        List<FollowersList> followerOfProfileDTOS = followers.stream().map(followUser ->
                new FollowersList(followUser.getUsername())
        ).collect(Collectors.toList());

        FollowersData followersData = new FollowersData(followerOfProfileDTOS, followers.size());

        List<FollowingsData> followingOfProfileDTOS = following.stream().map(followingUser ->
                new FollowingsData(followingUser.getUsername())
        ).collect(Collectors.toList());

        List<PostResponseDTO> postResponseDTOList = userPosts.stream().map(post -> {
            List<CommentPostResponseDTO> commentPostResponseDTOS = post.getComments().stream().map(comment ->
                    new CommentPostResponseDTO(comment.getUserId(), comment.getPost().getId(), comment.getUser().getUsername(),comment.getComment())).collect(Collectors.toList());

            Long quantityOfLikes = (long) post.getLikes().size();

            List<Like> listOflikes = post.getLikes().stream().map(Like::fromUser).toList();
            boolean userLikedThisPost = userId.equals(post.getUser().getId());


            return PostResponseDTO.fromData(post, post.getUser(), userLikedThisPost, quantityOfLikes,listOflikes,commentPostResponseDTOS);
        }).collect(Collectors.toList());


        return ProfileResponse.fromData(user, followersData, followingOfProfileDTOS, postResponseDTOList);
    }
    public String register(RegisterDTO registerDTO) throws Exception {

        this.registerValidate.forEach(v -> v.validate(registerDTO));

        if(Objects.equals(registerDTO.getEmail(), admMail)) {
            this.userRole = UserRole.ADMIN;
        } else {
            this.userRole = UserRole.USER;
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.getPassword());

        User newUser = new User(registerDTO.getName(), registerDTO.getEmail(), encryptedPassword, userRole);

        try {
            userRepository.save(newUser);
            return "Usuário registrado com sucesso!";
        } catch(Exception e) {
            throw new Exception("Erro no registro", e.getCause());
        }
    }

    public User update(UpdateDTO updateDTO, String userId) {

        this.updateValidate.validate(updateDTO);

        User userToBeUpdated = userRepository.findById(userId).
                orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        userToBeUpdated.setName(updateDTO.name());
        userToBeUpdated.setEmail(updateDTO.email());
        userToBeUpdated.setPassword(updateDTO.password());

        return userRepository.save(userToBeUpdated);

    }

    public String delete(String userId) {

        Optional<User> userToBeDeleted = this.userRepository.findById(userId);

        if(userToBeDeleted.isPresent()) {
            this.userRepository.delete(userToBeDeleted.get());
            return userToBeDeleted.get().getEmail();
        }
        return null;
    }

    public String changeProfilePicture(ProfileImg profileImg, String userId) {

        Optional<User> user = this.userRepository.findById(userId);
        String pictureUrl = this.amazonClient.uploadFile(profileImg.getFile());

        if(user.isPresent()) {
            user.get().setUserProfilePhotoUrl(pictureUrl);
            userRepository.save(user.get());

            return "Imagem atualizada!";
        }

        return null;
    }


    public User signin(String userId) {
        return this.userRepository.findById(userId).get();
    }
}























