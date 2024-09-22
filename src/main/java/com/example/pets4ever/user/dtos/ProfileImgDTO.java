package com.example.pets4ever.user.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileImgDTO {
  private MultipartFile file;
}
