package com.example.pets4ever.post;

import com.example.pets4ever.post.DTO.PostDTO;
import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.post.DTO.PostShowDTO;
import com.example.pets4ever.post.DTO.UpdatePostToReceiveLikeDTO;
import com.example.pets4ever.post.response.CreateResponse;
import com.example.pets4ever.utils.GetUserIdFromToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/post")
public class PostController {

    @Autowired
    PostServices postServices;
    @Autowired
    GetUserIdFromToken getUserIdFromToken;

    @GetMapping("/")
    public ResponseEntity<List<PostResponseDTO>> index(){
        return ResponseEntity.ok().body(this.postServices.getAllPosts());
    }

    //busca o post de acordo com o usuario para tratar likes etc
    //fazer no futuro um apenas para post, sem depender de user
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> show(@PathVariable String postId, @RequestBody PostShowDTO postShowDTO){
        return ResponseEntity.ok().body(this.postServices.getPost(postId,postShowDTO.userId()));
    }

    @PostMapping("/")
    public ResponseEntity<CreateResponse> create(@ModelAttribute @Valid PostDTO postDTO) {
        System.out.println(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(postServices.create(postDTO));
    }

//    @PostMapping("/createStorie")
//    public ResponseEntity<Post> createStorie(@ModelAttribute PostDTO postDTO, @RequestHeader("Authorization") String bearerToken) {
//        String userId = getUserIdFromToken.recoverUserId(bearerToken);
//
//        Post newStorie = postServices.create(postDTO, userId);
//
//        return ResponseEntity.ok().body(newStorie);
//    }

    @PostMapping("/postlike/{userId}")
    public ResponseEntity<String> updatePostToReceiveLike(@PathVariable String userId, @ModelAttribute UpdatePostToReceiveLikeDTO data) {
        return ResponseEntity.ok().body(this.postServices.UpdatePostToReceiveLikesService(data.getPostId(), userId));
    }

}
