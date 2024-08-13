package com.example.pets4ever.user;

import com.example.pets4ever.user.dtos.changeProfileImageDTO.ProfileImg;
import com.example.pets4ever.user.dtos.signupDTO.RegisterDTO;
import com.example.pets4ever.user.dtos.updateDTO.UpdateDTO;
import com.example.pets4ever.user.responses.ProfileResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid RegisterDTO data) throws Exception {
        return ResponseEntity.ok(userService.create(data));
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<User> update(@PathVariable String userId, @RequestBody UpdateDTO updateDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(updateDTO, userId));
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.delete(userId));
    }

    @PutMapping("/change_profile_picture/{userId}")
    public ResponseEntity<String> changeProfilePicture(@PathVariable String userId, @ModelAttribute ProfileImg profileImg, @RequestHeader("Authorization") String bearerToken){
        return ResponseEntity.ok(userService.changeProfilePicture(profileImg, userId));
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<ProfileResponse> profile(@PathVariable String id) throws Exception {
        ProfileResponse user = userService.profile(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}






