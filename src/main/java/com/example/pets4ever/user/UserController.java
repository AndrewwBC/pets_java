package com.example.pets4ever.user;

import com.example.pets4ever.user.dtos.PatchProfileDTO;
import com.example.pets4ever.user.dtos.ProfileImgDTO;
import com.example.pets4ever.user.dtos.DeleteFollowerDTO;
import com.example.pets4ever.user.dtos.PatchFollowingDTO;
import com.example.pets4ever.user.dtos.SignInDTO;
import com.example.pets4ever.user.dtos.UpdateEmailDTO;
import com.example.pets4ever.user.responses.*;
import com.example.pets4ever.utils.GetUsernameFromToken;
import com.example.pets4ever.utils.MyCookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    GetUsernameFromToken getUsernameFromToken;


    @GetMapping("")
    public ResponseEntity<UserResponse> user(HttpServletRequest request) {

        String username = getUsernameFromToken.recoverUsername(request);

        UserResponse user = userService.userByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> userByUsername(@PathVariable String username) {
        UserResponse user = userService.userByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        String jwt = null;

        MyCookie myCookie = new MyCookie();

        ResponseCookie jwtCookie = myCookie.generateCookie("jwt", jwt, 0, true, true);
        ResponseCookie hasSession = myCookie.generateCookie("hasSession", "yes", 0, false, false);

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, hasSession.toString());

        return ResponseEntity.ok().headers(headers).body("Sessão encerrada");
    }
    
    @PostMapping
    public ResponseEntity<Object> signup(@RequestBody @Valid SignInDTO data) {
        return ResponseEntity.ok(userService.create(data));
    }

    @PatchMapping("/email/{id}")
    public ResponseEntity<UpdateEmailResponse> email(@PathVariable String id, @RequestBody @Valid UpdateEmailDTO updateEmailDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateEmail(updateEmailDTO, id));
    }

    @PatchMapping("/profile/{id}")
    public ResponseEntity<Response> profile(@PathVariable String id, @RequestBody @Valid PatchProfileDTO patchProfileDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.patchProfile(id, patchProfileDTO));
    }

    @PatchMapping("/{id}/profile-img")
    public ResponseEntity<Response> profileImg(@PathVariable String id, @ModelAttribute ProfileImgDTO profileImgDTO) throws IOException {
        System.out.println(profileImgDTO);
        return ResponseEntity.ok(userService.patchProfileImg(profileImgDTO, id));
    }

    @PatchMapping("/following")
    public ResponseEntity<Response> following(@RequestBody @Valid PatchFollowingDTO patchFollowingDTO){
        System.out.println("rota following");
        System.out.println(patchFollowingDTO);
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






