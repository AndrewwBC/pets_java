package com.example.pets4ever.user.dtos.changeProfileImageDTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileImg {
  private MultipartFile file;
}
