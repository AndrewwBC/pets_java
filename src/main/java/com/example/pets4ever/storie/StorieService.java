package com.example.pets4ever.storie;

import com.example.pets4ever.infra.aws.AmazonClient;
import com.example.pets4ever.post.Post;
import com.example.pets4ever.post.PostRepository;
import com.example.pets4ever.storie.DTO.StorieDTO;
import com.example.pets4ever.storie.Processor.VideoProcessor;
import com.example.pets4ever.storie.Response.StorieResponse;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StorieService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VideoProcessor videoProcessor;

    @Autowired
    AmazonClient amazonClient;


    public List<StorieResponse> show(){
        List<Storie> Stories = this.postRepository.findAllStories();

        return Stories.stream().map((storie) ->
                new StorieResponse(storie.getImageUrl(), storie.getUser().getProfileImgUrl(), storie.getUser().getUsername(), storie.getExpirationTime())).toList();
    }
    public String create(StorieDTO storieDTO){
        User user = this.userRepository.findById(storieDTO.userId()).orElseThrow(() ->
                new NoSuchElementException("Usuário não encontrado!"));

        try {

            MultipartFile video = this.videoProcessor.resizeGit(storieDTO.file());

            String awsFilename = this.amazonClient.uploadVideo(video);
            Storie storieToBeInserted = new Storie(storieDTO.description(), awsFilename, user);

            this.postRepository.save(storieToBeInserted);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            return "create";

        }
    }

}
