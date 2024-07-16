package com.example.pets4ever.post;

import com.example.pets4ever.post.DTO.PostDTO;
import com.example.pets4ever.post.DTO.PostResponseDTO;
import com.example.pets4ever.utils.GetUserIdFromToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostServices postServices;
    @Autowired
    GetUserIdFromToken getUserIdFromToken;
    @GetMapping("/show")
    public ResponseEntity<List<PostResponseDTO>> show(){

        List<PostResponseDTO> allPosts = this.postServices.getPosts();
        return ResponseEntity.ok().body(allPosts);
    }

    @PostMapping("/create")
    public ResponseEntity<Post> create(@ModelAttribute PostDTO postDTO, @RequestHeader("Authorization") String bearerToken) {
        String userId = getUserIdFromToken.userId(bearerToken);

        System.out.println(postDTO.getIsStorie());

        Post newPost = postServices.insert(postDTO, userId);
        return ResponseEntity.status(HttpStatus.OK).body(newPost);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(@RequestHeader("Authorization") String bearerToken) throws JsonProcessingException {
        String userId = getUserIdFromToken.userId(bearerToken);

        return ResponseEntity.ok(postServices.profile(userId));

    }

    @PostMapping("/createStorie")
    public ResponseEntity<Post> createStorie(@ModelAttribute PostDTO postDTO, @RequestHeader("Authorization") String bearerToken) {
        String userId = getUserIdFromToken.userId(bearerToken);

        Post newStorie = postServices.insert(postDTO, userId);

        return ResponseEntity.ok().body(newStorie);
    }
}
