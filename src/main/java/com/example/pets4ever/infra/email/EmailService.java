package com.example.pets4ever.infra.email;

import com.example.pets4ever.infra.email.Code.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private CacheService cacheService;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("petsmail.tsi@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);

    }

    public String sendCodeToConfirmEmailUpdate(String email){

        String code = cacheService.storageCodeAndReturnIt();
        String emailText = "Este é o código " + code;

        this.sendSimpleMessage(email, "CODIGO VERIFICAÇÃO", emailText);

        return email;
    }
    public String sendNewCodeToConfirmEmailUpdate(String email){

        String code = cacheService.storageCodeAndReturnIt();
        String emailText = "Este é o código " + code;

        this.sendSimpleMessage(email, "RENOVAÇÃO DE CÓDIGO", emailText);

        return email;
    }



}
