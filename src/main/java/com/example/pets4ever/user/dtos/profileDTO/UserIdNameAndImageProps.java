package com.example.pets4ever.user.dtos.profileDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UserIdNameAndImageProps {

    private String name;
    private String userId;
    private String profileImgUrl;

    @Override
    public String toString() {
        return "UserIdNameAndImgProps{" +
                "name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", profileImgUrl='" + profileImgUrl + '\'' +
                '}';
    }
}
