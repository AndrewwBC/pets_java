package com.example.pets4ever.emailservices;

import com.example.pets4ever.emailservices.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
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