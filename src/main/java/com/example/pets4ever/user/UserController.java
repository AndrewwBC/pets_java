package com.example.pets4ever.user;

import com.example.pets4ever.user.dtos.PatchNameDTO.PatchUsernameDTO;
import com.example.pets4ever.user.dtos.changeProfileImageDTO.ProfileImg;
import com.example.pets4ever.user.dtos.patchFollowers.DeleteFollowerDTO;
import com.example.pets4ever.user.dtos.patchFollowing.PatchFollowingDTO;
import com.example.pets4ever.user.dtos.signUpDTO.signUpDTO;
import com.example.pets4ever.user.dtos.updateDTO.UpdateDTO;
import com.example.pets4ever.user.dtos.updateEmailDTO.UpdateEmailDTO;
import com.example.pets4ever.user.responses.*;
import com.example.pets4ever.utils.GetUserIdFromToken;
import com.example.pets4ever.utils.MyCookie;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @Autowired
    GetUserIdFromToken getUserIdFromToken;


    @GetMapping("")
    public ResponseEntity<UserResponse> user(HttpServletRequest request) {

        String id = getUserIdFromToken.recoverUserId(request);

        UserResponse user = userService.user(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        String jwt = null;

        MyCookie myCookie = new MyCookie();

        Cookie jwtCookie = myCookie.generateCookie("jwt", jwt, 0, true, true);
        Cookie hasSession = myCookie.generateCookie("hasSession", "yes", 0, false, false);

        response.addCookie(jwtCookie);
        response.addCookie(hasSession);

        return ResponseEntity.ok("Sessão encerrada.");
    }
    
    @PostMapping
    public ResponseEntity<Object> signup(@RequestBody @Valid signUpDTO data) throws Exception {
        return ResponseEntity.ok(userService.create(data));
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody UpdateDTO updateDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(updateDTO, id));
    }

    @PatchMapping("/email/{id}")
    public ResponseEntity<UpdateEmailResponse> email(@PathVariable String id, @RequestBody @Valid UpdateEmailDTO updateEmailDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateEmail(updateEmailDTO, id));
    }

    @PatchMapping("/name/{id}")
    public ResponseEntity<Response> username(@PathVariable String id, @RequestBody @Valid PatchUsernameDTO patchUsernameDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.patchName(id, patchUsernameDTO));
    }

    @PatchMapping("/{id}/profile-img")
    public ResponseEntity<Response> profileImg(@PathVariable String id, @ModelAttribute ProfileImg profileImg) throws IOException {
        System.out.println(profileImg);
        return ResponseEntity.ok(userService.patchProfileImg(profileImg, id));
    }

    @PatchMapping("/following")
    public ResponseEntity<Response> following(@RequestBody @Valid PatchFollowingDTO patchFollowingDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.patchFollowing(patchFollowingDTO));
    }

    @DeleteMapping("/follower")
    public ResponseEntity<Response> follower(@RequestBody @Valid DeleteFollowerDTO deleteFollowerDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteFollower(deleteFollowerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.delete(id));
    }
}






