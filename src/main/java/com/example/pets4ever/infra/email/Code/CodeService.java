package com.example.pets4ever.infra.email.Code;


import com.example.pets4ever.infra.email.Code.DTO.ValidateDTO;
import com.example.pets4ever.infra.email.Code.exceptions.ErrorMessage;
import com.example.pets4ever.infra.email.Code.exceptions.InvalidCodeException;
import com.example.pets4ever.infra.email.Code.response.CodeResponse;
import com.example.pets4ever.infra.email.Code.response.ValidateResponse;
import com.example.pets4ever.infra.email.EmailService;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CodeService {

    @Autowired
    CacheService cacheService;
    @Autowired
    EmailService emailService;
    @Autowired
    UserRepository userRepository;
    public ValidateResponse validate(ValidateDTO validateDTO) {

        boolean isCodeValid = cacheService.isCodeValid(validateDTO.code());
        System.out.println(validateDTO.code());
        if(isCodeValid) {
            cacheService.removeCode(validateDTO.code());

            User user = userRepository.findById(validateDTO.userId()).orElseThrow(()
                    -> new NoSuchElementException("Usuário não encontrado"));

            user.setEmail(validateDTO.email());

            userRepository.save(user);

            return new ValidateResponse("Código válido. Email atualizado");
        } else {
            ErrorMessage errorMessage = new ErrorMessage("Código inválido");
            throw new InvalidCodeException(errorMessage);
        }

    }


}
