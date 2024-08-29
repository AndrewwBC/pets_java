package com.example.pets4ever.user;


import com.example.pets4ever.infra.security.TokenService;
import com.example.pets4ever.infra.aws.AmazonClient;
import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.post.DTO.PostResponse.Like;
import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.dtos.changeProfileImageDTO.ProfileImg;
import com.example.pets4ever.user.dtos.profileDTO.FollowersData;
import com.example.pets4ever.user.dtos.profileDTO.UserIdNameAndImageProps;
import com.example.pets4ever.user.dtos.profileDTO.FollowingsData;
import com.example.pets4ever.user.dtos.profileDTO.UserPostsAndQuantityOfPosts;
import com.example.pets4ever.user.dtos.signupDTO.RegisterDTO;
import com.example.pets4ever.user.dtos.updateDTO.UpdateDTO;
import com.example.pets4ever.user.dtos.updateEmailDTO.UpdateEmailDTO;
import com.example.pets4ever.user.responses.ChangeProfileImageResponse;
import com.example.pets4ever.user.responses.ProfileResponse;
import com.example.pets4ever.user.responses.UpdateEmailResponse;
import com.example.pets4ever.user.validations.register.RegisterValidate;
import com.example.pets4ever.user.validations.update.UpdateValidate;
import com.example.pets4ever.user.validations.update.updateProfileImage.UpdateProfileImageValidate;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${ADM_MAIL}")
    private String admMail;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    List<RegisterValidate> registerValidate;

    @Autowired
    UpdateProfileImageValidate updateProfileImageValidate;

    @Autowired
    UpdateValidate updateValidate;
    private final AmazonClient amazonClient;

    @Autowired
    UserService(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    public List<User> index() {
        return this.userRepository.findAll();
    }
    public ProfileResponse profile(String userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        List<User> followers = user.getFollowers();
        List<User> following = user.getFollowing();
        List<Post> userPosts = user.getPosts();

        List<UserIdNameAndImageProps> listOfUsersToFollowersData = followers.stream().map(followUser ->
                new UserIdNameAndImageProps(followUser.getUsername(), followUser.getId(), followUser.getUserProfilePhotoUrl())
        ).collect(Collectors.toList());

        FollowersData followersData = new FollowersData(listOfUsersToFollowersData, followers.size());

        List<UserIdNameAndImageProps> listOfUsersToFollowingData = following.stream().map(followingUser ->
                new UserIdNameAndImageProps(followingUser.getUsername(), followingUser.getId(), followingUser.getUserProfilePhotoUrl())
        ).collect(Collectors.toList());

        FollowingsData followingsData = new FollowingsData(listOfUsersToFollowingData, listOfUsersToFollowingData.size());

        List<PostResponseDTO> postResponseDTOList = userPosts.stream().map(post -> {
            List<CommentPostResponseDTO> commentPostResponseDTOS = post.getComments().stream().map(comment ->
                    new CommentPostResponseDTO(comment.getUserId(), comment.getPost().getId(), comment.getUser().getUsername(), comment.getComment())).collect(Collectors.toList());

            Long quantityOfLikes = (long) post.getLikes().size();

            List<Like> listOflikes = post.getLikes().stream().map(Like::fromUser).toList();
            boolean userLikedThisPost = userId.equals(post.getUser().getId());


            return PostResponseDTO.fromData(post, post.getUser(), userLikedThisPost, quantityOfLikes, listOflikes, commentPostResponseDTOS);
        }).collect(Collectors.toList());

        UserPostsAndQuantityOfPosts userPostsAndQuantityOfPosts = new UserPostsAndQuantityOfPosts(postResponseDTOList, postResponseDTOList.size());

        return ProfileResponse.fromData(user, followersData, followingsData, userPostsAndQuantityOfPosts);
    }

    public String create(RegisterDTO registerDTO) throws Exception {

        this.registerValidate.forEach(v -> v.validate(registerDTO));

        UserRole userRole;
        if (Objects.equals(registerDTO.getEmail(), admMail)) {
            userRole = UserRole.ADMIN;
        } else {
            userRole = UserRole.USER;
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.getPassword());

        User newUser = new User(registerDTO.getName(), registerDTO.getEmail(), encryptedPassword, userRole);

        try {
            userRepository.save(newUser);
            return "Usuário registrado com sucesso!";
        } catch (Exception e) {
            throw new Exception("Erro no registro", e.getCause());
        }
    }

    public String update(UpdateDTO updateDTO, String userId) {

        this.updateValidate.validate(updateDTO);

        User userToBeUpdated = userRepository.findById(userId).
                orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        userToBeUpdated.setName(updateDTO.name());
        userToBeUpdated.setEmail(updateDTO.email());

        userRepository.save(userToBeUpdated);

        return "Usuário atualizado";
    }

    public UpdateEmailResponse updateEmail(UpdateEmailDTO updateEmailDTO, String userId) {

        User user = this.userRepository.findById(userId).orElseThrow(()
                -> new NoSuchElementException("Usuário não encontrado"));

        try {
            user.setEmail(updateEmailDTO.email());

            this.userRepository.save(user);
            return new UpdateEmailResponse("Email atualizado!");
        } catch(PersistenceException exception) {
            throw new PersistenceException("Erro ao editar o email.");
        }
    }

    public String delete(String userId) {

        Optional<User> userToBeDeleted = this.userRepository.findById(userId);

        if (userToBeDeleted.isPresent()) {
            this.userRepository.delete(userToBeDeleted.get());
            return userToBeDeleted.get().getEmail();
        }
        return null;
    }

    public ChangeProfileImageResponse changeProfilePicture(ProfileImg profileImg, String userId) {

        System.out.println(profileImg);
        this.updateProfileImageValidate.validate(profileImg.getFile());

        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("Usuário não encontrado."));
        try {
            String uniqueFilename = this.amazonClient.uploadFile(profileImg.getFile());
            user.setUserProfilePhotoUrl(uniqueFilename);

            userRepository.save(user);
            return new ChangeProfileImageResponse(profileImg.getFile().getName(), user.getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao mudar a imagem de perfil.");
        }
    }

    public User signin(String userId) {
        return this.userRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("Usuário não encontrado."));
    }
}























