package com.example.pets4ever.comment;


import com.example.pets4ever.comment.DTO.CommentDTO;
import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.post.Post;
import com.example.pets4ever.post.PostRepository;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    public String insertComment(CommentDTO commentDTO) {

        User user = this.userRepository.findById(commentDTO.getUserId()).orElseThrow(() ->
            new NoSuchElementException("Usuário não encontrado!"));

        Post post = this.postRepository.findById(commentDTO.getPostId()).orElseThrow(() ->
                new NoSuchElementException("Postagem não encontrada!"));

        Comment comment = new Comment(commentDTO.getComment(), user, post);

        try {
            this.commentRepository.save(comment);
        } catch(PersistenceException e) {
            throw new PersistenceException("Erro ao registrar comentário");
        }

        return "Okay";
    }

    public List<CommentPostResponseDTO> getCommentsFromPostId(String postId) {
        List<Comment> comments = null;

        this.postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("Postagem não encontrada. Verifique o ID!"));

        try {
            comments = this.commentRepository.findByPostId(postId);
        } catch (Exception e) {
            throw new NoSuchElementException("Comentários não encontrados!");
        }

        return comments.stream()
                .map(comment -> CommentPostResponseDTO.fromData(comment.getUser(), comment.getComment())).toList();
    }
}
