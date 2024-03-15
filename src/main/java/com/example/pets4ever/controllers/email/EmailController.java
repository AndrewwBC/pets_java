package com.example.pets4ever.controllers.email;

import com.example.pets4ever.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sendEmail")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public String sendEmail() {
        emailService.sendSimpleMessage("andrewborgescampos@gmail.com", "Assunto do Email", "Conteúdo do Email");
        return "Email enviado com sucesso";
    }
}