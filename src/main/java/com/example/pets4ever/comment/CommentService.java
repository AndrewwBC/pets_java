package com.example.pets4ever.comment;


import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public List<CommentPostResponseDTO> getCommentsFromPostId(String postId) {
        List<Comment> comments = this.commentRepository.findByPostId(postId);

        List<CommentPostResponseDTO> postComments = comments.stream()
                .map(comment -> CommentPostResponseDTO.fromData(comment.getUser(), comment.getComment())).toList();

        return postComments;
    }
}
