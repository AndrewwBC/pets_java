package com.example.pets4ever.user;


import com.example.pets4ever.infra.exceptions.user.PatchFollowingException;
import com.example.pets4ever.infra.security.TokenService;
import com.example.pets4ever.infra.aws.AmazonClient;
import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.post.DTO.PostResponse.Like;
import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.dtos.PatchNameDTO.PatchUsernameDTO;
import com.example.pets4ever.user.dtos.changeProfileImageDTO.ProfileImg;
import com.example.pets4ever.user.dtos.patchFollowers.DeleteFollowerDTO;
import com.example.pets4ever.user.dtos.patchFollowing.PatchFollowingDTO;
import com.example.pets4ever.user.dtos.profileDTO.FollowersData;
import com.example.pets4ever.user.dtos.profileDTO.UserIdNameAndImageProps;
import com.example.pets4ever.user.dtos.profileDTO.FollowingsData;
import com.example.pets4ever.user.dtos.profileDTO.UserPostsAndQuantityOfPosts;
import com.example.pets4ever.user.dtos.signUpDTO.signUpDTO;
import com.example.pets4ever.user.dtos.updateDTO.UpdateDTO;
import com.example.pets4ever.user.dtos.updateEmailDTO.UpdateEmailDTO;
import com.example.pets4ever.user.responses.*;
import com.example.pets4ever.user.validations.register.RegisterValidate;
import com.example.pets4ever.user.validations.update.UpdateValidate;
import com.example.pets4ever.user.validations.update.updateProfileImage.UpdateProfileImageValidate;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public UserResponse user(String userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        System.out.println(user);

        List<User> followers = user.getFollowedByUsers();
        List<User> following = user.getFollowingUsers();
        List<Post> userPosts = user.getPosts();

        List<UserIdNameAndImageProps> listOfUsersToFollowersData = followers.stream().map(followUser ->
                new UserIdNameAndImageProps(followUser.getUsername(), followUser.getId(), followUser.getProfileImgUrl())
        ).collect(Collectors.toList());

        FollowersData followersData = new FollowersData(listOfUsersToFollowersData, followers.size());

        List<UserIdNameAndImageProps> listOfUsersToFollowingData = following.stream().map(followingUser ->
                new UserIdNameAndImageProps(followingUser.getUsername(), followingUser.getId(), followingUser.getProfileImgUrl())
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

        return UserResponse.fromData(user, followersData, followingsData, userPostsAndQuantityOfPosts);
    }

    public UserResponse userByUsername(String username) {

        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));


        List<User> followers = user.getFollowedByUsers();
        List<User> following = user.getFollowingUsers();
        List<Post> userPosts = user.getPosts();

        List<UserIdNameAndImageProps> listOfUsersToFollowersData = followers.stream().map(followUser ->
                new UserIdNameAndImageProps(followUser.getUsername(), followUser.getId(), followUser.getProfileImgUrl())
        ).collect(Collectors.toList());

        FollowersData followersData = new FollowersData(listOfUsersToFollowersData, followers.size());

        List<UserIdNameAndImageProps> listOfUsersToFollowingData = following.stream().map(followingUser ->
                new UserIdNameAndImageProps(followingUser.getUsername(), followingUser.getId(), followingUser.getProfileImgUrl())
        ).collect(Collectors.toList());

        FollowingsData followingsData = new FollowingsData(listOfUsersToFollowingData, listOfUsersToFollowingData.size());

        List<PostResponseDTO> postResponseDTOList = userPosts.stream().map(post -> {
            List<CommentPostResponseDTO> commentPostResponseDTOS = post.getComments().stream().map(comment ->
                    new CommentPostResponseDTO(comment.getUserId(), comment.getPost().getId(), comment.getUser().getUsername(), comment.getComment())).collect(Collectors.toList());

            Long quantityOfLikes = (long) post.getLikes().size();

            List<Like> listOflikes = post.getLikes().stream().map(Like::fromUser).toList();
            boolean userLikedThisPost = username.equals(post.getUser().getUsername());

            return PostResponseDTO.fromData(post, post.getUser(), userLikedThisPost, quantityOfLikes, listOflikes, commentPostResponseDTOS);
        }).collect(Collectors.toList());

        UserPostsAndQuantityOfPosts userPostsAndQuantityOfPosts = new UserPostsAndQuantityOfPosts(postResponseDTOList, postResponseDTOList.size());

        return UserResponse.fromData(user, followersData, followingsData, userPostsAndQuantityOfPosts);
    }


    public String create(signUpDTO signupDTO) throws Exception {

        this.registerValidate.forEach(v -> v.validate(signupDTO));

        UserRole userRole;
        if (Objects.equals(signupDTO.getEmail(), admMail)) {
            userRole = UserRole.ADMIN;
        } else {
            userRole = UserRole.USER;
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(signupDTO.getPassword());
        User newUser = new User(signupDTO.getFullname(), signupDTO.getUsername(), signupDTO.getEmail(), encryptedPassword, userRole);

        userRepository.save(newUser);
        return "Usuário registrado com sucesso!";
    }

    public String update(UpdateDTO updateDTO, String userId) {

        this.updateValidate.validate(updateDTO);

        User userToBeUpdated = userRepository.findById(userId).
                orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        userToBeUpdated.setUsername(updateDTO.name());
        userToBeUpdated.setEmail(updateDTO.email());

        userRepository.save(userToBeUpdated);

        return "Usuário atualizado";
    }

    public UpdateEmailResponse updateEmail(UpdateEmailDTO updateEmailDTO, String userId) {

        User user = this.userRepository.findById(userId).orElseThrow(()
                -> new NoSuchElementException("Usuário não encontrado"));

        boolean emailAlreadyInUse = this.userRepository.existsByEmail(updateEmailDTO.email());
        if(emailAlreadyInUse) {
            return new UpdateEmailResponse("Email atualizado!");
        }

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

    public Response patchProfileImg(ProfileImg profileImg, String userId) throws IOException {

        System.out.println(profileImg);
        this.updateProfileImageValidate.validate(profileImg.getFile());

        User user = this.findUserByIdOrElseThrow(userId);


        String uniqueFilename = this.amazonClient.uploadFile(profileImg.getFile());
        user.setProfileImgUrl(uniqueFilename);

        userRepository.save(user);
        return Response.fromString("Imagem atualizada");

    }

    public User signin(String userId) {
        return this.userRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("Usuário não encontrado."));
    }

    public Response patchName(String userId, PatchUsernameDTO patchUsernameDTO) {

        User user = this.findUserByIdOrElseThrow(userId);

        user.setUsername(patchUsernameDTO.username());
        userRepository.save(user);

        return Response.fromString("Nome atualizado.");
    }


    @Transactional
    public  Response patchFollowing(PatchFollowingDTO patchFollowingDTO) {
        User actionUser = this.findUserByUsernameOrElseThrow(patchFollowingDTO.actionUserUsername());
        User userToBeFollowedOrUnfollowed = this.findUserByUsernameOrElseThrow(patchFollowingDTO.usernameOfUserToBeFollowed());

        if(actionUser == userToBeFollowedOrUnfollowed) {
            throw new PatchFollowingException("Usuário não pode seguir a si mesmo.");
        }

        String response;

        if(actionUser.getFollowingUsers().contains(userToBeFollowedOrUnfollowed)) {
            actionUser.getFollowingUsers().remove(userToBeFollowedOrUnfollowed);
            userToBeFollowedOrUnfollowed.getFollowedByUsers().remove(actionUser);


            response = "Você deixou de seguir o usuário " + userToBeFollowedOrUnfollowed.getUsername()+".";
        } else {
            actionUser.getFollowingUsers().add(userToBeFollowedOrUnfollowed);
            userToBeFollowedOrUnfollowed.getFollowedByUsers().add(actionUser);

            response = "Você está seguindo o usuário " + userToBeFollowedOrUnfollowed.getUsername()+".";
        }

        userRepository.save(actionUser);
        userRepository.save(userToBeFollowedOrUnfollowed);

        return Response.fromString(response);
    }

    @Transactional
    public Response deleteFollower(DeleteFollowerDTO deleteFollowerDTO) {

        System.out.println(deleteFollowerDTO);

        User user = findUserByIdOrElseThrow(deleteFollowerDTO.actionUserId());
        User followerToBeRemoved = findUserByIdOrElseThrow(deleteFollowerDTO.idOfFollowerToBeRemoved());

        if(user.getFollowedByUsers().contains(followerToBeRemoved)) {
            user.getFollowedByUsers().remove(followerToBeRemoved);
            followerToBeRemoved.getFollowingUsers().remove(user);

            userRepository.save(user);
            userRepository.save(followerToBeRemoved);

            return Response.fromString("O seguidor " + followerToBeRemoved.getUsername() + " foi removido");
        } else {
            throw new NoSuchElementException("Seguidor não encontrado");
        }

    }

    private User findUserByIdOrElseThrow(String userId) {

         return userRepository.findById(userId).orElseThrow(()
                    -> new NoSuchElementException("Usuário não encontrado."));
    }

    private User findUserByUsernameOrElseThrow(String username) {

        return userRepository.findByUsername(username).orElseThrow(()
                -> new NoSuchElementException("Usuário não encontrado."));
    }
}























