package com.example.pets4ever.user.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileImg {
  private MultipartFile file;
}
