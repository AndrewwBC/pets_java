package com.example.pets4ever.Controller;


import jakarta.servlet.annotation.HttpMethodConstraint;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")

public class User {

    @PostMapping
    @Validated
    public String showUserName() {

        return "Andrew";
    }

    @GetMapping
    public String showAndrewName() {
        return "Andrew";
    }
}
