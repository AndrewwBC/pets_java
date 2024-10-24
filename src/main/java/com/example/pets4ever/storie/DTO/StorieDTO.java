package com.example.pets4ever.storie.DTO;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
public record StorieDTO(
        String description,
        @NotNull(message = "Deve conter o ID do usuário!")
        String userId,
        @NotNull(message = "A postagem deve incluir uma imagem!")
        MultipartFile file
)
{}