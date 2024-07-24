package com.example.pets4ever.user;

import com.example.pets4ever.comment.DTO.CommentPostResponseDTO;
import com.example.pets4ever.post.DTO.PostResponse.Like;
import com.example.pets4ever.post.DTO.PostResponse.PostResponseDTO;
import com.example.pets4ever.post.Post;
import com.example.pets4ever.user.DTO.Profile.FollowersData;
import com.example.pets4ever.user.DTO.Profile.FollowersList;
import com.example.pets4ever.user.DTO.Profile.FollowingsData;
import com.example.pets4ever.user.DTO.Profile.UserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserServices userServices;

    @GetMapping("/profile/{id}")
    public ResponseEntity<UserProfileDTO> profile(@PathVariable String id) {

        System.out.println(id);
        UserProfileDTO user = userServices.profile(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}






