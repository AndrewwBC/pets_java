package com.example.pets4ever.userHistory;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "UserHistory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String teste;

    @Column(name = "sys_period")
    private String sysPeriod;

}
