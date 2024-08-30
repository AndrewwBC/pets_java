package com.example.pets4ever.infra.email.Code;


import com.example.pets4ever.infra.email.Code.DTO.NewCodeDTO;
import com.example.pets4ever.infra.email.Code.DTO.ValidateDTO;
import com.example.pets4ever.infra.email.Code.response.CodeResponse;
import com.example.pets4ever.infra.email.Code.response.ValidateResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/code")
public class CodeController {

    @Autowired
    CodeService codeService;

    @PostMapping("/validate")
    public ResponseEntity<ValidateResponse> validate(@RequestBody @Valid ValidateDTO validateDTO) {
        System.out.println(validateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(codeService.validate(validateDTO));
    }

}
