package com.example.pets4ever.post;


import com.example.pets4ever.aws.AmazonClient;
import com.example.pets4ever.post.DTO.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    private final AmazonClient amazonClient;
    @Autowired
    PostController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@ModelAttribute PostDTO postDTO) {
        System.out.println(postDTO.getFile());

        String fileUrl = amazonClient.uploadFile(postDTO.getFile());

        return ResponseEntity.status(HttpStatus.OK).body(fileUrl);
    }

}
