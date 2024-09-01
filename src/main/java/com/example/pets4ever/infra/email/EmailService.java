package com.example.pets4ever.infra.email;

import com.example.pets4ever.infra.email.Code.CacheService;
import com.example.pets4ever.infra.exceptions.email.EmailAlreadyInUseException;
import com.example.pets4ever.user.UserRepository;
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

    @Autowired
    UserRepository userRepository;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("petsmail.tsi@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);

    }

    public String sendCodeToConfirmEmailUpdate(String email){

        boolean emailAlreadyInUse =  userRepository.findByEmail(email).isPresent();

        if(emailAlreadyInUse) {
            throw new EmailAlreadyInUseException("Email já cadastrado.");
        }

        String code = cacheService.storageCodeAndReturnIt();
        String emailText = "Este é o código " + code;

        this.sendSimpleMessage(email, "CODIGO VERIFICAÇÃO", emailText);

        return email;
    }
    public String sendNewCodeToConfirmEmailUpdate(String email){

        boolean emailAlreadyInUse =  userRepository.findByEmail(email).isPresent();

        if(emailAlreadyInUse) {
            throw new EmailAlreadyInUseException("Email já cadastrado.");
        }

        String code = cacheService.storageCodeAndReturnIt();
        String emailText = "Este é o código " + code;

        this.sendSimpleMessage(email, "RENOVAÇÃO DE CÓDIGO", emailText);

        return email;
    }



}
