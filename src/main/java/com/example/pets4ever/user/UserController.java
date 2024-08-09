package com.example.pets4ever.user;

import com.example.pets4ever.user.dtos.ProfileImg;
import com.example.pets4ever.user.dtos.Register.RegisterDTO;
import com.example.pets4ever.user.dtos.UpdateDTO.UpdateDTO;
import com.example.pets4ever.user.responses.ChangeProfilePictureResponse;
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
    UserServices userServices;

    @GetMapping("/profile/{id}")
    public ResponseEntity<ProfileResponse> profile(@PathVariable String id) {
        ProfileResponse user = userServices.profile(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid RegisterDTO data) {
        return ResponseEntity.ok(userServices.register(data));
    }
    @PutMapping("/change_profile_picture/{userId}")
    public ResponseEntity<ChangeProfilePictureResponse> changeProfilePicture(@PathVariable String userId, @ModelAttribute ProfileImg profileImg, @RequestHeader("Authorization") String bearerToken){
        return ResponseEntity.ok(userServices.changeProfilePicture(profileImg, userId));
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<User> update(@PathVariable String userId, @RequestBody UpdateDTO updateDTO){
        User userUpdated = userServices.update(updateDTO, userId);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable String userId){
        String deletedUserEmail = userServices.delete(userId);
        return ResponseEntity.status(HttpStatus.OK).body(deletedUserEmail);
    }
}






