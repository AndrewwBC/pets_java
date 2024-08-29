package com.example.pets4ever.user.validations.update.updateProfileImage;

import org.springframework.web.multipart.MultipartFile;

public interface UpdateProfileImageValidate {
    void validate(MultipartFile file);
}
