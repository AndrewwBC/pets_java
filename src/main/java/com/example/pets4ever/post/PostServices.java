package com.example.pets4ever.post;

import com.example.pets4ever.infra.aws.AmazonClient;
import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.infra.exceptions.post.WrongFileType;
import com.example.pets4ever.post.DTO.PostDTO;
import com.example.pets4ever.post.DTO.PostResponse.Like;
import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.post.validations.PostValidations;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PostServices {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostValidations postValidations;
    private final AmazonClient amazonClient;
    @Autowired
    PostServices(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    public Post create(PostDTO postDTO, String userId) throws WrongFileType {

        this.postValidations.allValidations(postDTO);

        String fileName = amazonClient.uploadFile(postDTO.file());

        Optional<User> user = this.userRepository.findById(userId);

        if(user.isPresent()) {
            Post postToBeInserted = new Post(postDTO.description(), fileName, user.get());
            return this.postRepository.save(postToBeInserted);
        }
        return null;
    }
    public List<PostResponseDTO> getPosts(String userId) {
        System.out.println(userId);
        this.userRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("Usuário não encontrado. Verifique o ID!"));

        List<Post> allPosts =  this.postRepository.findAll();

        List<PostResponseDTO> response = allPosts.stream().map(post -> {

            return getPostResponseDTO(userId, post);
        }).toList();
        return response;
    }
    public String UpdatePostToReceiveLikesService(String postId, String userId){

        Post post = this.postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("Postagem não encontrada!"));
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("Usuário não encontrado!"));

        if(post.getLikes().contains(user)) {
            post.getLikes().remove(user);
            this.postRepository.save(post);
            return "Curtida removida.";
        }

        post.getLikes().add(user);
        this.postRepository.save(post);
        return "Curtida adicionada.";
    }

    public PostResponseDTO getPost(String postId, String userId) {

        Post post = this.postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("Erro ao capturar as postagens!"));

        return getPostResponseDTO(userId, post);

    }

    @NotNull
    private PostResponseDTO getPostResponseDTO(String userId, Post post) {
        boolean userLikedThisPost = post.getLikes().stream().anyMatch(like -> userId.equals(like.getId()));
        Long quantityOfLikes = (long) post.getLikes().size();

        List<CommentPostResponseDTO> commentPostResponseDTOS = new ArrayList<>();
        post.getComments().forEach(comment -> {
            commentPostResponseDTOS.add(new CommentPostResponseDTO(comment.getUserId(), comment.getUser().getUserProfilePhotoUrl(), comment.getUser().getUsername(), comment.getComment()));
        });

        List<Like> listOflikes = post.getLikes().stream().map(Like::fromUser).toList();

        return PostResponseDTO.fromData(post, post.getUser(), userLikedThisPost, quantityOfLikes,listOflikes,commentPostResponseDTOS);
    }

}
