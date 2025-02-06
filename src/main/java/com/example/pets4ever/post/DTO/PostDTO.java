package com.example.pets4ever.post.DTO;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record PostDTO(
        String description,
        @NotNull(message = "Deve conter o ID do usuário!")
        String userId,
        @NotNull(message = "A postagem deve incluir uma imagem!")
        MultipartFile file,
        String is_storie
)
{
}
