package com.example.pets4ever.infra.email.Code;


import com.example.pets4ever.infra.email.Code.exceptions.ErrorMessage;
import com.example.pets4ever.infra.email.Code.exceptions.InvalidCodeException;
import com.example.pets4ever.infra.email.Code.response.CodeResponse;
import com.example.pets4ever.infra.email.Code.response.ValidateResponse;
import com.example.pets4ever.infra.email.EmailService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeService {

    @Autowired
    CacheService cacheService;

    @Autowired
    EmailService emailService;
    public ValidateResponse validate(String code) {

        boolean isCodeValid = cacheService.isCodeValid(code);
        System.out.println(code);
        if(isCodeValid) {
            return new ValidateResponse("Código válido");
        } else {
            ErrorMessage errorMessage = new ErrorMessage("Código inválido");
            throw new InvalidCodeException(errorMessage);
        }

    }


}
