package com.example.pets4ever.comment;


import com.example.pets4ever.comment.DTO.CommentDTO;
import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.comment.DTO.PatchCommentDTO;
import com.example.pets4ever.post.PostRepository;
import com.example.pets4ever.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<String> save(@RequestBody CommentDTO commentDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.commentService.insertComment(commentDTO));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentPostResponseDTO>> getComments(@PathVariable String postId){
        return ResponseEntity.status(HttpStatus.OK).body(this.commentService.getCommentsFromPostId(postId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchComment(@PathVariable String id, @RequestBody PatchCommentDTO patchCommentDTO){
        return ResponseEntity.status(HttpStatus.OK).body(this.commentService.patchComment(id, patchCommentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        System.out.println(id);
        return ResponseEntity.status(HttpStatus.OK).body(this.commentService.deleteComment(id));
    }
}
