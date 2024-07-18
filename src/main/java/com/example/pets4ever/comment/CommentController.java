package com.example.pets4ever.comment;


import com.example.pets4ever.comment.DTO.CommentDTO;
import com.example.pets4ever.post.Post;
import com.example.pets4ever.post.PostRepository;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import com.example.pets4ever.utils.GetUserIdFromToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    GetUserIdFromToken getUserIdFromToken;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/store")
    public ResponseEntity<String> insertComment(@RequestBody CommentDTO commentDTO, @RequestHeader("Authorization") String bearerToken){

        System.out.println(commentDTO);
        String userId = getUserIdFromToken.recoverUserId(bearerToken);

        Optional<User> user = this.userRepository.findById(userId);
        Optional<Post> post = this.postRepository.findById(commentDTO.getPostId());

        if(user.isPresent() && post.isPresent()) {
            Comment commentToBeStored = new Comment(commentDTO.getComment(), user.get(), post.get());
            this.commentRepository.save(commentToBeStored);
            return ResponseEntity.status(HttpStatus.OK).body("Ok");
        } else {
            return null;
        }
    }
}
