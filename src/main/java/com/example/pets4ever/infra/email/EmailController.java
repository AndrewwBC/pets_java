package com.example.pets4ever.infra.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send/{email}")
    public ResponseEntity<String> sendEmail(@PathVariable String email) {

        System.out.println(email);

        emailService.sendSimpleMessage(email, "Assunto do Email", "Conteúdo do Email");
        return ResponseEntity.ok(email);
    }
}