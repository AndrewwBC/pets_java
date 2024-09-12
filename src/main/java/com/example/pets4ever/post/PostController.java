package com.example.pets4ever.post;

import com.example.pets4ever.post.DTO.FeedDTO;
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

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/post")
public class PostController {

    @Autowired
    PostServices postServices;
    @Autowired
    GetUserIdFromToken getUserIdFromToken;

    @GetMapping("/{username}")
    public ResponseEntity<List<PostResponseDTO>> index(@PathVariable String username){
        return ResponseEntity.ok().body(this.postServices.getAllPosts(username));
    }

    //busca o post de acordo com o usuario para tratar likes etc
    //fazer no futuro um apenas para post, sem depender de user
//    @GetMapping("/{postId}")
//    public ResponseEntity<PostResponseDTO> show(@PathVariable String postId, @RequestBody PostShowDTO postShowDTO){
//        return ResponseEntity.ok().body(this.postServices.getPost(postId,postShowDTO.userId()));
//    }

    @PostMapping
    public ResponseEntity<CreateResponse> create(@ModelAttribute @Valid PostDTO postDTO) throws IOException {
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

    @PatchMapping("/postlike")
    public ResponseEntity<String> patchPostLike(@RequestBody UpdatePostToReceiveLikeDTO data) {
        System.out.println(data);
        return ResponseEntity.ok().body(this.postServices.UpdatePostToReceiveLikesService(data.postId(), data.username()));
    }

}
