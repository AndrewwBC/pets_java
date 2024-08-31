package com.example.pets4ever.post;

import com.example.pets4ever.infra.aws.AmazonClient;
import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.infra.exceptions.post.WrongFileType;
import com.example.pets4ever.post.DTO.PostDTO;
import com.example.pets4ever.post.DTO.PostResponse.Like;
import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.post.response.CreateResponse;
import com.example.pets4ever.post.validations.PostValidations;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import jakarta.persistence.PersistenceException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public CreateResponse create(PostDTO postDTO) {

        this.postValidations.allValidations(postDTO);
        User user = this.userRepository.findById(postDTO.userId()).orElseThrow(() ->
                new NoSuchElementException("Usuário não encontrado!"));

        try {
            String uniqueFilename = this.amazonClient.uploadFile(postDTO.file());
            Post postToBeInserted = new Post(postDTO.description(), uniqueFilename, user);

            Post createdPost = this.postRepository.save(postToBeInserted);
            return new CreateResponse(createdPost.getCreationDate(), createdPost.getImageUrl());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao criar a postagem.");
        }

    }
    public List<PostResponseDTO> getAllPosts() {
        List<Post> allPosts =  this.postRepository.findAll();

        return allPosts.stream().map(post -> getPostResponseDTO(post.getUser().getId(), post)).toList();
    }
    public PostResponseDTO getPost(String postId, String userId) {

        Post post = this.postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("Erro ao capturar as postagens!"));

        return getPostResponseDTO(userId, post);

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

    @NotNull
    private PostResponseDTO getPostResponseDTO(String userId, Post post) {
        boolean userLikedThisPost = post.getLikes().stream().anyMatch(like -> userId.equals(like.getId()));
        Long quantityOfLikes = (long) post.getLikes().size();

        List<CommentPostResponseDTO> commentPostResponseDTOS = new ArrayList<>();
        post.getComments().forEach(comment -> {
            commentPostResponseDTOS.add(new CommentPostResponseDTO(comment.getUserId(), comment.getUser().getProfileImgUrl(), comment.getUser().getUsername(), comment.getComment()));
        });

        List<Like> listOflikes = post.getLikes().stream().map(Like::fromUser).toList();

        return PostResponseDTO.fromData(post, post.getUser(), userLikedThisPost, quantityOfLikes,listOflikes,commentPostResponseDTOS);
    }

}
