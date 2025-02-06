package com.example.pets4ever.user;


import com.example.pets4ever.infra.email.Code.CacheService;
import com.example.pets4ever.infra.email.Code.exceptions.ErrorMessage;
import com.example.pets4ever.infra.email.Code.exceptions.InvalidCodeException;
import com.example.pets4ever.infra.exceptions.user.PatchFollowingException;
import com.example.pets4ever.infra.security.TokenService;
import com.example.pets4ever.infra.aws.AmazonClient;
import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.post.DTO.PostResponse.Like;
import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.post.Post;
import com.example.pets4ever.storie.Storie;
import com.example.pets4ever.user.dtos.*;
import com.example.pets4ever.user.dtos.profileDTO.FollowersData;
import com.example.pets4ever.user.dtos.profileDTO.UserIdNameAndImageProps;
import com.example.pets4ever.user.dtos.profileDTO.FollowingsData;
import com.example.pets4ever.user.dtos.profileDTO.UserPostsAndQuantityOfPosts;
import com.example.pets4ever.user.enums.UserRole;
import com.example.pets4ever.user.responses.*;
import com.example.pets4ever.user.validations.UserValidations;
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
    CacheService cacheService;

    @Autowired
    UserValidations userValidations;

    private final AmazonClient amazonClient;

    @Autowired
    UserService(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    public UserResponse userByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        return userData(user);
    }

    public UserResponse userById(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("Usuário não encontrado"));
        return userData(user);
    }

    private UserResponse userData(User user) {

        String username = user.getUsername();

        List<User> followers = user.getFollowedByUsers();
        List<User> following = user.getFollowingUsers();
        List<Post> userPosts = user.getPosts();

        System.out.println(userPosts);

        List<UserIdNameAndImageProps> listOfUsersToFollowersData = followers.stream().map(followUser ->
                new UserIdNameAndImageProps(followUser.getUsername(), followUser.getId(), followUser.getProfileImgUrl())
        ).collect(Collectors.toList());

        FollowersData followersData = new FollowersData(listOfUsersToFollowersData, followers.size());

        List<UserIdNameAndImageProps> listOfUsersToFollowingData = following.stream().map(followingUser ->
                new UserIdNameAndImageProps(followingUser.getUsername(), followingUser.getId(), followingUser.getProfileImgUrl())
        ).collect(Collectors.toList());

        FollowingsData followingsData = new FollowingsData(listOfUsersToFollowingData, listOfUsersToFollowingData.size());

        List<PostResponseDTO> postResponseDTOList = userPosts.stream().filter(post -> !(post instanceof Storie)).map(post -> {
            List<CommentPostResponseDTO> commentPostResponseDTOS = post.getComments().stream().map(comment ->
                    CommentPostResponseDTO.fromData(comment.getUser(), comment)).collect(Collectors.toList());

            Long quantityOfLikes = (long) post.getLikes().size();

            List<Like> listOflikes = post.getLikes().stream().map(Like::fromUser).toList();
            boolean userLikedThisPost = username.equals(post.getUser().getUsername());

            return PostResponseDTO.fromData(post, post.getUser(), userLikedThisPost, quantityOfLikes, listOflikes, commentPostResponseDTOS);
        }).collect(Collectors.toList());

        UserPostsAndQuantityOfPosts userPostsAndQuantityOfPosts = new UserPostsAndQuantityOfPosts(postResponseDTOList, postResponseDTOList.size());

        return UserResponse.fromData(user, followersData, followingsData, userPostsAndQuantityOfPosts);
    }


    public String create(SignUpDTO signupDTO) {

        this.userValidations.signUpValidate(signupDTO);

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

    public MessageResponse updateEmail(UpdateEmailDTO updateEmailDTO, String userId) {

        User user = this.userRepository.findById(userId).orElseThrow(()
                -> new NoSuchElementException("Usuário não encontrado"));

        boolean emailAlreadyInUse = this.userRepository.existsByEmail(updateEmailDTO.email());
        if(emailAlreadyInUse) {
            return new MessageResponse("Email atualizado!");
        }

        try {
            user.setEmail(updateEmailDTO.email());

            this.userRepository.save(user);
            return new MessageResponse("Email atualizado!");
        } catch(PersistenceException exception) {
            throw new PersistenceException("Erro ao editar o email.");
        }
    }

    public String delete(String userId) {
        User user = findUserByIdOrElseThrow(userId);
        this.userRepository.delete(user);
        return "Usuário excluido";
    }

    public FieldMessageResponse patchProfileImg(ProfileImgDTO profileImgDTO, String userId) throws IOException {

        this.userValidations.patchProfileImage(profileImgDTO.getFile());

        User user = this.findUserByIdOrElseThrow(userId);

        String uniqueFilename = this.amazonClient.uploadFile(profileImgDTO.getFile());
        user.setProfileImgUrl(uniqueFilename);

        userRepository.save(user);
        return FieldMessageResponse.fromData("image", uniqueFilename, "Imagem atualizada");

    }

    public List<FieldMessageResponse> patchProfile(String userId, PatchProfileDTO patchProfileDTO) {

        User user = this.findUserByIdOrElseThrow(userId);

        if(!Objects.equals(patchProfileDTO.username(), user.getUsername())) {
            this.userValidations.patchProfileValidate(patchProfileDTO);
        }

        List<FieldMessageResponse> response = new ArrayList<>();

        if(!Objects.equals(patchProfileDTO.fullname(), user.getFullname())) {
            response.add(FieldMessageResponse.fromData("fullname", user.getFullname(), "Atualizado"));
        }

        if(!Objects.equals(patchProfileDTO.username(), user.getUsername())){
            response.add(FieldMessageResponse.fromData("username", user.getUsername(), "Atualizado"));
        }

        user.setBio(patchProfileDTO.bio());
        user.setUsername(patchProfileDTO.username());
        user.setFullname(patchProfileDTO.fullname());
        userRepository.save(user);

        return response;
    }

    @Transactional
    public MessageResponse patchFollowing(PatchFollowingDTO patchFollowingDTO) {
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

        return new MessageResponse(response);
    }

    @Transactional
    public MessageResponse deleteFollower(DeleteFollowerDTO deleteFollowerDTO) {

        System.out.println(deleteFollowerDTO);

        User user = findUserByIdOrElseThrow(deleteFollowerDTO.actionUserId());
        User followerToBeRemoved = findUserByIdOrElseThrow(deleteFollowerDTO.idOfFollowerToBeRemoved());

        if(user.getFollowedByUsers().contains(followerToBeRemoved)) {
            user.getFollowedByUsers().remove(followerToBeRemoved);
            followerToBeRemoved.getFollowingUsers().remove(user);

            userRepository.save(user);
            userRepository.save(followerToBeRemoved);

            return new MessageResponse("O seguidor " + followerToBeRemoved.getUsername() + " foi removido");
        } else {
            throw new NoSuchElementException("Seguidor não encontrado");
        }

    }

    public MessageResponse patchPassword(String username, PatchPasswordDTO patchPasswordDTO) {

        this.userValidations.patchPassword(username, patchPasswordDTO);

        User user = this.findUserByUsernameOrElseThrow(username);

        String encryptedPassword = new BCryptPasswordEncoder().encode(patchPasswordDTO.newPassword());
        user.setPassword(encryptedPassword);
        this.userRepository.save(user);

        return new MessageResponse("Senha atualizada");
    }

    public MessageResponse forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {

        String code = forgotPasswordDTO.code();
        String username = forgotPasswordDTO.username();
        String newPassword = forgotPasswordDTO.newPassword();

        boolean isCodeValid = cacheService.isCodeValid(code);

        if(isCodeValid) {
            cacheService.removeCode(code);
        } else {
            ErrorMessage errorMessage = new ErrorMessage("Código inválido");
            throw new InvalidCodeException(errorMessage);
        }

        User user = this.findUserByUsernameOrElseThrow(username);

        String encryptedPassword = new BCryptPasswordEncoder().encode(newPassword);
        user.setPassword(encryptedPassword);
        this.userRepository.save(user);

        return new MessageResponse("Senha atualizada");
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























