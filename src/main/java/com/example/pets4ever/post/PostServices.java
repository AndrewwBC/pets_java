package com.example.pets4ever.post;

import com.example.pets4ever.infra.aws.AmazonClient;
import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.infra.exceptions.post.WrongFileType;
import com.example.pets4ever.post.DTO.PostDTO;
import com.example.pets4ever.post.DTO.PostResponse.Like;
import com.example.pets4ever.post.DTO.PostResponse.PatchDescription;
import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.post.response.CreateResponse;
import com.example.pets4ever.post.validations.PostValidations;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import com.example.pets4ever.user.responses.MessageResponse;
import jakarta.persistence.PersistenceException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

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

    public CreateResponse create(PostDTO postDTO) throws IOException {

        this.postValidations.allValidations(postDTO);
        User user = this.userRepository.findById(postDTO.userId()).orElseThrow(() ->
                new NoSuchElementException("Usuário não encontrado!"));


        String uniqueFilename = this.amazonClient.uploadFile(postDTO.file());
        Post postToBeInserted = new Post(postDTO.description(), uniqueFilename, user);

        Post createdPost = this.postRepository.save(postToBeInserted);
        System.out.println(createdPost);
        return new CreateResponse(createdPost.getCreationDate(), createdPost.getImageUrl());
    }

    public List<PostResponseDTO> getAllPosts(String username) {
        List<Post> allPosts =  this.postRepository.findAllNonStories();

        userRepository.findByUsername(username).orElseThrow(() ->
            new NoSuchElementException("Usuário não encontrado."));

        return allPosts.stream().map(post -> getPostResponseDTO(username, post)).toList();
    }
    public PostResponseDTO getPost(String postId, String username) {
        Post post = this.postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("Erro ao capturar as postagens!"));

        return getPostResponseDTO(username, post);
    }
    public PostResponseDTO UpdatePostToReceiveLikesService(String postId, String username){

        Post post = this.postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("Postagem não encontrada!"));
        User user = this.userRepository.findByUsername(username).orElseThrow(() ->
                new NoSuchElementException("Usuário não encontrado!"));

        if(post.getLikes().contains(user)) {
            post.getLikes().remove(user);
            this.postRepository.save(post);

            return getPostResponseDTO(username, post);
        }

        post.getLikes().add(user);
        this.postRepository.save(post);
        return getPostResponseDTO(username, post);

    }

    public MessageResponse patchDescription(PatchDescription patchDescription){

        Post post = postRepository.findById(patchDescription.postId()).orElseThrow(() ->
                new NoSuchElementException("Post não encontrado"));

        post.setDescription(patchDescription.description());
        postRepository.save(post);

        return new MessageResponse("Descrição atualizada");
    }

    @NotNull
    private PostResponseDTO getPostResponseDTO(String username, Post post) {
        boolean userLikedThisPost = post.getLikes().stream().anyMatch(like -> username.equals(like.getUsername()));
        Long quantityOfLikes = (long) post.getLikes().size();

        List<CommentPostResponseDTO> commentPostResponseDTOS = new ArrayList<>();
        post.getComments().forEach(comment -> {
            commentPostResponseDTOS.add(CommentPostResponseDTO.fromData(comment.getUser(), comment));
        });

        List<Like> listOflikes = post.getLikes().stream().map(Like::fromUser).toList();

        return PostResponseDTO.fromData(post, post.getUser(), userLikedThisPost, quantityOfLikes,listOflikes,commentPostResponseDTOS);
    }
    
    @Transactional
    public MessageResponse delete(String id){

        Post post = this.postRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Postagem não encontrada"));

        this.postRepository.deleteById(id);
        System.out.println(post.getImageUrl());
        this.amazonClient.deleteFileFromS3Bucket(post.getImageUrl());

        return new MessageResponse("Postagem deletada");
    }

}
