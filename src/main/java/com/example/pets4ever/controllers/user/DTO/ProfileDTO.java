package com.example.pets4ever.controllers.user.DTO;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileDTO {
    private String id;
    private String username;

    public ProfileDTO(String id, String username) {
        this.id = id;
        this.username = username;
    }


}
