package com.example.pets4ever.post;

import com.example.pets4ever.post.DTO.PostDTO;
import com.example.pets4ever.post.DTO.PostResponse.PatchDescription;
import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.post.DTO.UpdatePostToReceiveLikeDTO;
import com.example.pets4ever.post.response.CreateResponse;
import com.example.pets4ever.user.responses.MessageResponse;
import com.example.pets4ever.utils.GetIdFromToken;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    @Autowired
    PostServices postServices;
    @Autowired
    GetIdFromToken getIdFromToken;

    @GetMapping("/{username}")
    public ResponseEntity<List<PostResponseDTO>> index(@PathVariable String username) {
        return ResponseEntity.ok().body(this.postServices.getAllPosts(username));
    }

    @GetMapping("/show/{postId}/{username}")
    public ResponseEntity<PostResponseDTO> show(@PathVariable String postId, @PathVariable String username) {
        System.out.println(postId + username);
        return ResponseEntity.ok().body(this.postServices.getPost(postId, username));
    }

    @PostMapping("")
    public ResponseEntity<CreateResponse> create(@ModelAttribute @Valid PostDTO postDTO) throws IOException {
        System.out.println(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(postServices.create(postDTO));
    }

    @PatchMapping("/postlike")
    public ResponseEntity<PostResponseDTO> patchPostLike(@RequestBody UpdatePostToReceiveLikeDTO data) {
        System.out.println(data);
        return ResponseEntity.status(HttpStatus.OK).body(this.postServices.UpdatePostToReceiveLikesService(data.postId(), data.username()));
    }

    @PatchMapping("/description")
    public ResponseEntity<MessageResponse> patchDescription(@RequestBody PatchDescription patchDescription) {
        System.out.println(patchDescription);
        return ResponseEntity.status(HttpStatus.OK).body(this.postServices.patchDescription(patchDescription));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.postServices.delete(id));
    }
}
