package com.example.pets4ever.post;

import com.example.pets4ever.aws.AmazonClient;
import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.post.DTO.PostDTO;
import com.example.pets4ever.post.DTO.PostResponse.Like;
import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServices {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;
    private final AmazonClient amazonClient;
    @Autowired
    PostServices(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    public Post insert(PostDTO postDTO, String userId) {
        String fileName = amazonClient.uploadFile(postDTO.getFile());

        Optional<User> user = this.userRepository.findById(userId);

        if(user.isPresent()) {
            Post postToBeInserted = new Post(postDTO.getDescription(), fileName, postDTO.getCreationDate(), user.get());
            return this.postRepository.save(postToBeInserted);
        }
        return null;
    }
    public List<PostResponseDTO> getPosts(String userId) {
        List<Post> allPosts =  this.postRepository.findAll();

        List<PostResponseDTO> response = allPosts.stream().map(post -> {

            boolean userLikedThisPost = post.getLikes().stream().anyMatch(like -> userId.equals(like.getId()));
            Long quantityOfLikes = (long) post.getLikes().size();

            List<CommentPostResponseDTO> commentPostResponseDTOS = new ArrayList<>();
            post.getComments().forEach(comment -> {
                commentPostResponseDTOS.add(new CommentPostResponseDTO(comment.getUserId(), comment.getPost().getId(), comment.getComment()));
            });

            List<Like> listOflikes = post.getLikes().stream().map(Like::fromUser).toList();

            return PostResponseDTO.fromData(post, post.getUser(), userLikedThisPost, quantityOfLikes,listOflikes,commentPostResponseDTOS);
        }).toList();
        return response;
    }

    public String UpdatePostToReceiveLikesService(String postId, String userId){

        Post post = this.postRepository.findById(postId).get();
        User user = this.userRepository.findById(userId).get();

        if(post.getLikes().contains(user)) {
            post.getLikes().remove(user);
            this.postRepository.save(post);

            return user.getName();
        }

        post.getLikes().add(user);
        this.postRepository.save(post);
        return user.getName();
    }

}
