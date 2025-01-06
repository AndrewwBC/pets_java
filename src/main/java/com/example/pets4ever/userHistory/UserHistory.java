package com.example.pets4ever.userHistory;

import com.example.pets4ever.user.User;
import com.vladmihalcea.hibernate.type.range.Range;
import io.hypersistence.utils.hibernate.type.range.PostgreSQLRangeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_history")
@Data
@Setter
@Getter
public class UserHistory {

    @Id
    private String userId;

    @Id
    @Type(PostgreSQLRangeType.class)
    @Column(name = "sys_period", columnDefinition = "tstzrange")
    private Range<LocalDateTime> sysPeriod;

    @Column(name = "role")
    private Short role;

    @Column(name = "username")
    @Length(min = 3, max = 32, message = "Nome de usuário de 3 a 32 caractéres!")
    @NotEmpty(message = "Nome de usuário deve ser preenchido")
    private String username;

    @Length(min = 8,max = 64, message = "Senha com ao menos 8 caractéres!")
    @Column(name = "password")
    private String password;

    @Column(name = "bio")
    @Length(max = 128, message = "No máximo 128 caractéres")
    private String bio;

    @Column(name = "fullname")
    @Length(min = 1, max = 128, message = "Nome completo deve ter de 3 a 128 caractéres")
    private String fullname;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_img_url")
    private String profileImgUrl;
}
