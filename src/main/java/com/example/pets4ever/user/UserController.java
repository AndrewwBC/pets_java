package com.example.pets4ever.user;

import com.example.pets4ever.user.dtos.PatchNameDTO.PatchNameDTO;
import com.example.pets4ever.user.dtos.changeProfileImageDTO.ProfileImg;
import com.example.pets4ever.user.dtos.patchFollowing.PatchFollowingDTO;
import com.example.pets4ever.user.dtos.signupDTO.RegisterDTO;
import com.example.pets4ever.user.dtos.updateDTO.UpdateDTO;
import com.example.pets4ever.user.dtos.updateEmailDTO.UpdateEmailDTO;
import com.example.pets4ever.user.responses.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    
    @PostMapping
    public ResponseEntity<Object> signup(@RequestBody @Valid RegisterDTO data) throws Exception {
        return ResponseEntity.ok(userService.create(data));
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody UpdateDTO updateDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(updateDTO, id));
    }

    @PatchMapping("/email/{id}")
    public ResponseEntity<UpdateEmailResponse> updateEmail(@PathVariable String id, @RequestBody @Valid UpdateEmailDTO updateEmailDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateEmail(updateEmailDTO, id));
    }

    @PatchMapping("/name/{id}")
    public ResponseEntity<PatchNameResponse> updateName(@PathVariable String id, @RequestBody @Valid PatchNameDTO patchNameDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.patchName(id, patchNameDTO));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.delete(id));
    }

    @PatchMapping("/{id}/profile-img")
    public ResponseEntity<ChangeProfileImageResponse> patchProfileImg(@PathVariable String id, @ModelAttribute ProfileImg profileImg) throws IOException {
        System.out.println(profileImg);
        return ResponseEntity.ok(userService.patchProfileImg(profileImg, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> profile(@PathVariable String id) {
        System.out.println(id);
        ProfileResponse user = userService.profile(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PatchMapping("/following")
    public ResponseEntity<PatchFollowingResponse> patchFollowing(@RequestBody @Valid PatchFollowingDTO patchFollowingDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.patchFollowing(patchFollowingDTO));
    }
}






