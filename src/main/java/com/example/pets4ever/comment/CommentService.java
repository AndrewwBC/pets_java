package com.example.pets4ever.comment;


import com.example.pets4ever.comment.DTO.CommentDTO;
import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.comment.DTO.PatchCommentDTO;
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

        User user = this.userRepository.findById(commentDTO.userId()).orElseThrow(() ->
            new NoSuchElementException("Usuário não encontrado!"));

        Post post = this.postRepository.findById(commentDTO.postId()).orElseThrow(() ->
                new NoSuchElementException("Postagem não encontrada!"));

        Comment comment = new Comment(commentDTO.comment(), user, post);

        try {
            this.commentRepository.save(comment);
        } catch(PersistenceException e) {
            throw new PersistenceException("Erro ao registrar comentário");
        }

        return "Comentário inserido";
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
                .map(comment -> CommentPostResponseDTO.fromData(comment.getUser(), comment)).toList();
    }


    public String patchComment(String id, PatchCommentDTO patchCommentDTO) {
        Comment comment = this.commentOrElseThrow(id);

        comment.setComment(patchCommentDTO.comment());
        this.commentRepository.save(comment);

        return "Comentário editado";
    }

    public String deleteComment(String id){
        this.commentRepository.deleteById(id);
        return "Comentário deletado";
    }

    private Comment commentOrElseThrow(String id) {
        return this.commentRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("Comentário não encontrado."));
    }
}
