package com.example.pets4ever.user;


import com.example.pets4ever.Infra.TokenService;
import com.example.pets4ever.aws.AmazonClient;
import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.post.DTO.PostResponse.Like;
import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.dtos.*;
import com.example.pets4ever.user.dtos.Profile.FollowersData;
import com.example.pets4ever.user.dtos.Profile.FollowersList;
import com.example.pets4ever.user.dtos.Profile.FollowingsData;
import com.example.pets4ever.user.dtos.Register.RegisterDTO;
import com.example.pets4ever.user.dtos.UpdateDTO.UpdateDTO;
import com.example.pets4ever.user.responses.ChangeProfilePictureResponse;
import com.example.pets4ever.user.responses.ProfileResponse;
import com.example.pets4ever.user.validations.register.RegisterValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    List<RegisterValidate> registerValidate;
    private final AmazonClient amazonClient;
    @Autowired
    UserServices(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    public ProfileResponse profile(String userId) {

        User user =  this.userRepository.findById(userId).get();

        List<User> followers = user.getFollowers();
        List<User> following = user.getFollowing();
        List<Post> userPosts = user.getPosts();

        List<FollowersList> followerOfProfileDTOS = followers.stream().map(followUser ->
                new FollowersList(followUser.getUsername())
        ).collect(Collectors.toList());

        FollowersData followersData = new FollowersData(followerOfProfileDTOS, followers.stream().count());

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
    public User register(RegisterDTO registerDTO) {

        this.registerValidate.forEach(v -> v.validate(registerDTO));

        if(Objects.equals(registerDTO.getEmail(), admMail)) {
            this.userRole = UserRole.ADMIN;
        } else {
            this.userRole = UserRole.USER;
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.getPassword());

        User newUser = new User(registerDTO.getName(), registerDTO.getEmail(), encryptedPassword, userRole);
        return userRepository.save(newUser);
    }

    public User update(UpdateDTO updateDTO, String userId) {

        ///this.validate.updateValidate(updateDTO);

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

    public String delete(String userId) {

        Optional<User> userToBeDeleted = this.userRepository.findById(userId);

        if(userToBeDeleted.isPresent()) {
            this.userRepository.delete(userToBeDeleted.get());
            return userToBeDeleted.get().getEmail();
        }
        return null;
    }

    public ChangeProfilePictureResponse changeProfilePicture(ProfileImg profileImg, String userId) {

        Optional<User> user = this.userRepository.findById(userId);
        String pictureUrl = this.amazonClient.uploadFile(profileImg.getFile());

        if(user.isPresent()) {
            user.get().setUserProfilePhotoUrl(pictureUrl);
            userRepository.save(user.get());

            return new ChangeProfilePictureResponse("Imagem atualizada!");
        }

        return null;
    }

    public User signin(String userId) {
        return this.userRepository.findById(userId).get();
    }
}























