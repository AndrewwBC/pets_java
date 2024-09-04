package com.example.pets4ever.post.DTO;

import com.example.pets4ever.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record PostDTO(
        String description,
        @NotNull(message = "Deve conter o ID do usuário!")
        String userId,
        @NotNull(message = "A postagem deve incluir uma imagem!")
        MultipartFile file,
        String is_storie
)
{
    public PostDTO(String description,String userId,MultipartFile file, String is_storie) {
        this.description = description;
        this.userId = userId;
        this.file = file;
        this.is_storie = is_storie;
    }
}
