package com.example.pets4ever.user.validations;

import com.example.pets4ever.user.dtos.PatchProfileDTO;
import com.example.pets4ever.user.dtos.SignUpDTO;
import com.example.pets4ever.user.dtos.SignInDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserValidate {

    void signUpValidate(SignUpDTO signupDTO);
    void signInValidate(SignInDTO signInDTO);
    void patchProfileValidate(PatchProfileDTO patchProfileDTO);
    void patchProfileImage(MultipartFile file);

}
