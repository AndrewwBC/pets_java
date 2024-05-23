package com.example.pets4ever.post;

import com.example.pets4ever.aws.AmazonClient;
import com.example.pets4ever.post.DTO.PostDTO;
import com.example.pets4ever.post.DTO.PostResponseDTO;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<PostResponseDTO> getPosts() {

        List<Post> allPosts =  this.postRepository.findAll();

        List<PostResponseDTO> response = new ArrayList<>();

        allPosts.forEach(post -> {
            response.add(new PostResponseDTO(post.getId(), post.getDescription(), post.getImageUrl(), post.getCreationDate(), post.getUser().getName(),post.getUser().getUserProfilePhotoUrl(), post.getUser().getId()));
        });
        return response;
    }
}
