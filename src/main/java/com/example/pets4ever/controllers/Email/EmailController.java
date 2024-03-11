package com.example.pets4ever.controllers.Email;

import com.example.pets4ever.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendEmail")
    public String sendEmail() {
        emailService.sendSimpleMessage("andrewborgescampos@gmail.com", "Assunto do Email", "Conteúdo do Email");
        return "Email enviado com sucesso!";
    }
}