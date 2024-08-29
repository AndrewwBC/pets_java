package com.example.pets4ever.user;

import com.example.pets4ever.user.dtos.changeProfileImageDTO.ProfileImg;
import com.example.pets4ever.user.dtos.signupDTO.RegisterDTO;
import com.example.pets4ever.user.dtos.updateDTO.UpdateDTO;
import com.example.pets4ever.user.dtos.updateEmailDTO.UpdateEmailDTO;
import com.example.pets4ever.user.responses.ChangeProfileImageResponse;
import com.example.pets4ever.user.responses.ProfileResponse;
import com.example.pets4ever.user.responses.UpdateEmailResponse;
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
    @GetMapping
    public ResponseEntity<Object> index() {
        return ResponseEntity.ok(userService);
    }
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.delete(id));
    }

    @PutMapping("/{id}/profile-picture")
    public ResponseEntity<ChangeProfileImageResponse> updateProfilePicture(@PathVariable String id, @ModelAttribute ProfileImg profileImg){
        System.out.println(profileImg);
        return ResponseEntity.ok(userService.changeProfilePicture(profileImg, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> profile(@PathVariable String id) {
        ProfileResponse user = userService.profile(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}






