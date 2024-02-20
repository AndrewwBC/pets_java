package com.example.pets4ever.Controller;


import jakarta.servlet.annotation.HttpMethodConstraint;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class User {

    @RequestMapping(value = "/users/andrew", method = RequestMethod.GET)
    @Validated
    public String showAndrewName() {
        return "Andrew";
    }





}
