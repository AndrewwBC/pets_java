package com.example.pets4ever.post;

import com.example.pets4ever.post.DTO.PostDTO;
import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.post.DTO.PostShowDTO;
import com.example.pets4ever.post.DTO.UpdatePostToReceiveLikeDTO;
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
    @GetMapping("/show")
    public ResponseEntity<List<PostResponseDTO>> show(@RequestBody PostShowDTO postShowDTO){
        return ResponseEntity.ok().body(this.postServices.getPosts(postShowDTO.userId()));
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity.BodyBuilder create(@PathVariable String userId, @ModelAttribute @Valid PostDTO postDTO) {
        postServices.create(postDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(@RequestHeader("Authorization") String bearerToken) throws JsonProcessingException {
        String userId = getUserIdFromToken.recoverUserId(bearerToken);

        return ResponseEntity.ok("Oi");

    }

    @PostMapping("/createStorie")
    public ResponseEntity<Post> createStorie(@ModelAttribute PostDTO postDTO, @RequestHeader("Authorization") String bearerToken) {
        String userId = getUserIdFromToken.recoverUserId(bearerToken);

        Post newStorie = postServices.create(postDTO, userId);

        return ResponseEntity.ok().body(newStorie);
    }

    @PostMapping("/postlike")
    public ResponseEntity<String> updatePostToReceiveLike(@ModelAttribute UpdatePostToReceiveLikeDTO data, @RequestHeader("Authorization") String bearerToken) {
        String userId = getUserIdFromToken.recoverUserId(bearerToken);

        System.out.println(data);

        String response = this.postServices.UpdatePostToReceiveLikesService(data.getPostId(), userId);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}/{userId}")
    public ResponseEntity<?> getPost(@PathVariable String id, @PathVariable String userId) {
        PostResponseDTO postResponseDTO = this.postServices.getPost(id, userId);
        return ResponseEntity.status(HttpStatus.OK).body(postResponseDTO);
    }
}
