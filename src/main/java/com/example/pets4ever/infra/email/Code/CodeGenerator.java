package com.example.pets4ever.infra.email.Code;

import lombok.Setter;

import java.util.Random;

@Setter
public class CodeGenerator {
    public String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
