package com.example.pets4ever.infra.email;

import com.example.pets4ever.infra.email.Code.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    @Autowired
    private EmailService emailService;
    @PostMapping("/send/{email}")
    public ResponseEntity<String> sendEmail(@PathVariable String email) {

        System.out.println(email);

        emailService.sendSimpleMessage(email, "Assunto do Email", "Conteúdo do Email");
        return ResponseEntity.ok(email);
    }

    @PostMapping("/send/updateEmailCode/{email}")
    public ResponseEntity<String> sendCodeToConfirmEmailUpdate(@PathVariable String email) {
       return ResponseEntity.status(HttpStatus.OK).body(emailService.sendCodeToConfirmEmailUpdate(email));
    }

    @PostMapping("/send/renewUpdateEmailCode/{email}")
    public ResponseEntity<String> sendNewCodeToConfirmEmailUpdate(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.sendNewCodeToConfirmEmailUpdate(email));
    }
}