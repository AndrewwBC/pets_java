package com.example.pets4ever.storie;

import com.example.pets4ever.storie.DTO.StorieDTO;
import com.example.pets4ever.storie.Response.StorieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/storie")
public class StorieController {

    @Autowired
    StorieService storieService;

    @GetMapping("/")
    public ResponseEntity<List<StorieResponse>> show(){
        return ResponseEntity.ok().body(this.storieService.show());
    }

    @PostMapping("/")
    public ResponseEntity<String> post(@ModelAttribute StorieDTO storieDTO){
        System.out.println(storieDTO);
        return ResponseEntity.ok().body(this.storieService.create(storieDTO));
    }
}
